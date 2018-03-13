package us.infinz.pawelcwieka.organiser.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import us.infinz.pawelcwieka.organiser.dao.ConfigurationDAO;
import us.infinz.pawelcwieka.organiser.dao.UserDAO;
import us.infinz.pawelcwieka.organiser.resource.Configuration;
import us.infinz.pawelcwieka.organiser.resource.User;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterWindowController implements Initializable{

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void handleRegisterButton(){

        String login = loginTextField.getText();
        String password1 = passwordTextField1.getText();
        String password2 = passwordTextField1.getText();
        String email = emailTextField.getText();

        User user = new User();

        user.setUserLogin(login);
        user.setUserPassword(password1);
        user.setUserEmail(email);

        Configuration configuration = new Configuration();
        configuration.setConfigurationForecastRefresh("5");

        user.setConfiguration(configuration);

        UserDAO userDAO = new UserDAO();
        userDAO.saveUser(user);


    };
}
