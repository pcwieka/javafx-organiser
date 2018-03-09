package us.infinz.pawelcwieka.organiser.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import us.infinz.pawelcwieka.organiser.api.DarkSky;
import us.infinz.pawelcwieka.organiser.api.GoogleGeocoding;
import us.infinz.pawelcwieka.organiser.dao.ForecastDAO;
import us.infinz.pawelcwieka.organiser.dao.ForecastDAOImpl;
import us.infinz.pawelcwieka.organiser.dao.LocalisationDAO;
import us.infinz.pawelcwieka.organiser.dao.LocalisationDAOImpl;
import us.infinz.pawelcwieka.organiser.exception.EmailException;
import us.infinz.pawelcwieka.organiser.resource.Forecast;
import us.infinz.pawelcwieka.organiser.resource.Localization;
import us.infinz.pawelcwieka.organiser.service.CalendarCreator;
import us.infinz.pawelcwieka.organiser.service.MessageWindowProvider;
import us.infinz.pawelcwieka.organiser.util.Configuration;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SettingsWindowController implements Initializable {

    @FXML
    private ComboBox localisationComboBox;
    @FXML
    private TextField emailTextField;
    @FXML
    private Button quitButton;
    @FXML
    private ComboBox refreshingForecastPeriodComboBox;

    private ObservableList<Localization> localizations;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@gmail.com$", Pattern.CASE_INSENSITIVE);



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        localizations = FXCollections.observableArrayList();

        LocalisationDAO localisationDAO = new LocalisationDAOImpl();

        localizations.addAll(localisationDAO.findAllLocalisations());

        localisationComboBox.getItems().addAll(localizations);

        Localization activeLocalization = localisationDAO.findActiveLocalisation();

        if(activeLocalization !=null){

            localisationComboBox.setValue(activeLocalization);
        }

        emailTextField.setText(Configuration.getProperty("email"));

        refreshingForecastPeriodComboBox.getItems().addAll("5", "10", "15","20","30","45","60");

        refreshingForecastPeriodComboBox.setValue(Configuration.getProperty("forecast"));



    }

    @FXML
    private void handleSaveButton(){

        boolean localisationExistsAlready = false;

        try {

            if (!emailTextField.getText().isEmpty()) {

                if (!validateEmailAddress(emailTextField.getText())) {

                    throw new EmailException();

                }

            }

        }catch(EmailException e){

            MessageWindowProvider messageWindowProvider = new MessageWindowProvider(

                    "Niepoprawny adres email",
                    "Wprowadź poprawny adres email w domenie gmail.com i spróbuj ponownie."
            );

            messageWindowProvider.showMessageWindow();

            emailTextField.setText("");

        }

        Configuration.saveProperty("email",emailTextField.getText());
        Configuration.saveProperty("forecast", (String) refreshingForecastPeriodComboBox.getValue());

        Object localisationString = localisationComboBox.getValue();

        if(localisationString != null) {

            GoogleGeocoding googleGeocoding = new GoogleGeocoding(localisationString.toString());
            Localization localization = googleGeocoding.getLocalisationDetails();

            if (localization != null) {

                LocalisationDAO localisationDAO = new LocalisationDAOImpl();

                localizations.clear();

                localizations.addAll(localisationDAO.findAllLocalisations());

                for (Localization lc : localizations) {

                    if (lc.getUserTypedName().equals(localization.getUserTypedName())) {

                        localisationExistsAlready = true;

                    }

                }

                localisationDAO.updateLocalisationsActiveColumn(false);

                if (!localisationExistsAlready) {

                    localisationDAO.saveLocalisation(localization);

                    localizations.add(localization);

                } else {

                    localisationDAO.updateLocalisationActiveColumn(localization, true);

                }

                localisationComboBox.getItems().clear();

                localisationComboBox.getItems().addAll(localizations);

                localisationComboBox.setValue(localization);

                DarkSky darkSky = new DarkSky();

                Forecast forecast = darkSky.getForecast(localization);

                ForecastDAO forecastDAO = new ForecastDAOImpl();
                forecastDAO.deleteAllForecasts();
                forecastDAO.saveForecast(forecast);

            }

            CalendarCreator calendarCreator = CalendarCreator.getInstance();
            calendarCreator.createCalendar();

        }



    }


    @FXML
    private void handleQuitButton(){

        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();

    }

    private boolean validateEmailAddress(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }


}
