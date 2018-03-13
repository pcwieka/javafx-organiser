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
import us.infinz.pawelcwieka.organiser.dao.*;
import us.infinz.pawelcwieka.organiser.exception.EmailException;
import us.infinz.pawelcwieka.organiser.resource.Configuration;
import us.infinz.pawelcwieka.organiser.resource.Forecast;
import us.infinz.pawelcwieka.organiser.resource.Localization;
import us.infinz.pawelcwieka.organiser.resource.User;
import us.infinz.pawelcwieka.organiser.service.CalendarCreator;
import us.infinz.pawelcwieka.organiser.service.MessageWindowProvider;

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

    private User user;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@gmail.com$", Pattern.CASE_INSENSITIVE);



    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    @FXML
    private void handleSaveButton(){

        IUserDAO userDAO = new UserDAO();

        user = userDAO.findUser(user.getId());

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

        //Configuration.saveProperty("email",emailTextField.getText());
        user.setUserEmail(emailTextField.getText());
        //Configuration.saveProperty("forecast", (String) refreshingForecastPeriodComboBox.getValue());

        Configuration configuration = new Configuration();
        configuration.setConfigurationForecastRefresh((String)refreshingForecastPeriodComboBox.getValue());

        user.setConfiguration(configuration);

        Object localisationString = localisationComboBox.getValue();

        if(localisationString != null) {

            GoogleGeocoding googleGeocoding = new GoogleGeocoding(localisationString.toString());
            Localization localization = googleGeocoding.getLocalisationDetails();

            if (localization != null) {

                ILocalisationDAO localisationDAO = new LocalisationDAO();

                localizations.clear();

                localizations.addAll(user.getLocalizations());

                Long localizationId = null;

                for (Localization lc : localizations) {

                    if (lc.getUserTypedName().equals(localization.getUserTypedName())) {

                        localisationExistsAlready = true;
                        localizationId = lc.getId();
                        break;

                    }

                }

                localisationDAO.updateLocalisationsActiveColumn(user, false);

                if (!localisationExistsAlready) {

                    user.getLocalizations().add(localization);
                    userDAO.saveUser(user);

                    localizations.add(localization);

                } else {

                    localisationDAO.updateLocalisationActiveColumn(user,localization, true);

                }

                localisationComboBox.getItems().clear();

                localisationComboBox.getItems().addAll(localizations);

                localisationComboBox.setValue(localization);

                DarkSky darkSky = new DarkSky();

                Forecast forecast = darkSky.getForecast(localization);

                localization.setForecast(forecast);
                localization.setUser(user);

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

    public void init(User user){

        localizations = FXCollections.observableArrayList();

        IUserDAO userDAO = new UserDAO();

        this.user = userDAO.findUser(user.getId());

        localisationComboBox.getItems().addAll(localizations);

        ILocalisationDAO localisationDAO = new LocalisationDAO();

        Localization activeLocalization = localisationDAO.findActiveLocalisation(user);

        if(activeLocalization !=null){

            localisationComboBox.setValue(activeLocalization);
        }

        emailTextField.setText(user.getUserEmail());

        refreshingForecastPeriodComboBox.getItems().addAll("5", "10", "15","20","30","45","60");

        refreshingForecastPeriodComboBox.setValue(user.getConfiguration().getConfigurationForecastRefresh());

    }


}
