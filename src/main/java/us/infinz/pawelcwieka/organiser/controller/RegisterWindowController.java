package us.infinz.pawelcwieka.organiser.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import us.infinz.pawelcwieka.organiser.dao.ConfigurationDAO;
import us.infinz.pawelcwieka.organiser.dao.UserDAO;
import us.infinz.pawelcwieka.organiser.exception.EmailException;
import us.infinz.pawelcwieka.organiser.resource.Configuration;
import us.infinz.pawelcwieka.organiser.resource.User;
import us.infinz.pawelcwieka.organiser.service.MessageWindowProvider;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterWindowController implements Initializable {

    @FXML
    private TextField loginTextField;

    @FXML
    private TextField passwordTextField1;

    @FXML
    private TextField passwordTextField2;

    @FXML
    private TextField emailTextField;

    @FXML
    private Button registerButton;

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@gmail.com$", Pattern.CASE_INSENSITIVE);


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void handleRegisterButton() {

        String login = loginTextField.getText();
        String password1 = passwordTextField1.getText();
        String password2 = passwordTextField2.getText();
        String email = emailTextField.getText();

        UserDAO userDAO = new UserDAO();
        User databaseUser = userDAO.findUserByLogin(login);

        if (login == null || login.isEmpty() || password1 == null || password1.isEmpty() || password2 == null || password2.isEmpty()) {

            MessageWindowProvider messageWindowProvider = new MessageWindowProvider(

                    "Błąd podczas rejestracji",
                    "Uzupełnij wszystkie wymagane pola: login, hasło, powtórz hasło."
            );

            messageWindowProvider.showMessageWindow();

        } else if (databaseUser != null && login.equals(databaseUser.getUserLogin())) {

            MessageWindowProvider messageWindowProvider = new MessageWindowProvider(

                    "Błąd podczas rejestracji",
                    "Użytkownik o podanym loginie już istnieje. Wprowadź inny login."
            );

            messageWindowProvider.showMessageWindow();


        } else if (!password1.equals(password2)) {

            MessageWindowProvider messageWindowProvider = new MessageWindowProvider(

                    "Błąd podczas rejestracji",
                    "Wprowadzone hasła nie pasują do siebie."
            );

            messageWindowProvider.showMessageWindow();


        } else {

                try {

                    if (!email.isEmpty()) {

                            if (!validateEmailAddress(email)) {

                                throw new EmailException();

                            }

                    }

                    User user = new User();

                    user.setUserLogin(login);
                    user.setUserPassword(password1);
                    user.setUserEmail(email);

                    Configuration configuration = new Configuration();
                    configuration.setConfigurationForecastRefresh("5");

                    user.setConfiguration(configuration);

                    userDAO.saveUser(user);

                    Stage stage = (Stage) loginTextField.getScene().getWindow();
                    stage.close();

                    MessageWindowProvider messageWindowProvider = new MessageWindowProvider(

                            "Status rejestracji",
                            "Rejestracja użytkownika " + login + " została zakończona pomyślnie."
                    );

                    messageWindowProvider.showMessageWindow();

                } catch (EmailException e) {

                    MessageWindowProvider messageWindowProvider = new MessageWindowProvider(

                            "Niepoprawny adres email",
                            "Wprowadź poprawny adres email w domenie gmail.com i spróbuj ponownie."
                    );

                    messageWindowProvider.showMessageWindow();
                }


            }

        }

    private boolean validateEmailAddress(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }
}
