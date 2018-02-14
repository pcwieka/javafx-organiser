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
import us.infinz.pawelcwieka.organiser.resource.Localisation;
import us.infinz.pawelcwieka.organiser.service.CalendarCreator;
import us.infinz.pawelcwieka.organiser.service.MessageWindowProvider;
import us.infinz.pawelcwieka.organiser.util.Configuration;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
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

    private ObservableList<Localisation> localisations;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@gmail.com$", Pattern.CASE_INSENSITIVE);



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        localisations = FXCollections.observableArrayList();

        LocalisationDAO localisationDAO = new LocalisationDAOImpl();

        localisations.addAll(localisationDAO.findAllLocalisations());

        localisationComboBox.getItems().addAll(localisations);

        Localisation activeLocalisation = localisationDAO.findActiveLocalisation();

        if(activeLocalisation!=null){

            localisationComboBox.setValue(activeLocalisation);
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
            Localisation localisation = googleGeocoding.getLocalisationDetails();

            if (localisation != null) {

                LocalisationDAO localisationDAO = new LocalisationDAOImpl();

                localisations.clear();

                localisations.addAll(localisationDAO.findAllLocalisations());

                for (Localisation lc : localisations) {

                    if (lc.getUserTypedName().equals(localisation.getUserTypedName())) {

                        localisationExistsAlready = true;

                    }

                }

                localisationDAO.updateLocalisationsActiveColumn(false);

                if (!localisationExistsAlready) {

                    localisationDAO.saveLocalisation(localisation);

                    localisations.add(localisation);

                } else {

                    localisationDAO.updateLocalisationActiveColumn(localisation, true);

                }

                localisationComboBox.getItems().clear();

                localisationComboBox.getItems().addAll(localisations);

                localisationComboBox.setValue(localisation);

                DarkSky darkSky = new DarkSky();

                Forecast forecast = darkSky.getForecast(localisation);

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
