package us.infinz.pawelcwieka.organiser.controller;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import us.infinz.pawelcwieka.organiser.CalendarOrganiserMain;
import us.infinz.pawelcwieka.organiser.dao.UserDAO;
import us.infinz.pawelcwieka.organiser.resource.Configuration;
import us.infinz.pawelcwieka.organiser.resource.User;
import us.infinz.pawelcwieka.organiser.service.CalendarCreator;
import us.infinz.pawelcwieka.organiser.service.CryptWithMD5;
import us.infinz.pawelcwieka.organiser.service.MessageWindowProvider;
import us.infinz.pawelcwieka.organiser.service.UserSession;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginWindowController implements Initializable {

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void handleLoginButton(){

        String login = loginTextField.getText();
        String password = CryptWithMD5.cryptWithMD5(passwordTextField.getText());

        UserDAO userDAO = new UserDAO();
        User user = userDAO.findUserByLogin(login);

        if(user == null ){

            MessageWindowProvider messageWindowProvider = new MessageWindowProvider(

                    "Błąd podczas logowania",
                    "Użytkownik o podanym loginie nie istnieje. Wprowadź poprawny login lub zarejestruj się."
            );

            messageWindowProvider.showMessageWindow();

        } else if (user.getAuthorization() && !user.getUserPassword().trim().equals(password)){

            MessageWindowProvider messageWindowProvider = new MessageWindowProvider(

                    "Błąd podczas logowania",
                    "Niepoprawny login użytkownika lub hasło."
            );

            messageWindowProvider.showMessageWindow();

        } else {

            UserSession.setUserSessionActive(user,true);

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();

            CalendarCreator calendarCreator = CalendarCreator.getInstance();
            calendarCreator.setUser(user);
            calendarCreator.startThread();

            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/CalendarOrganiserMainWindow.fxml"));

                Parent root = (Parent)loader.load();

                CalendarOrganiserMainWindowController calendarOrganiserMainWindowController = loader.getController();
                calendarOrganiserMainWindowController.setParams(user);

                stage = new Stage();
                Scene primaryScene = new Scene(root);
                primaryScene.getStylesheets().add("stylesheet.css");
                stage.setTitle("Organizer: " + user.getUserLogin());
                stage.setScene(primaryScene);
                stage.setResizable(true);

                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent t) {

                        UserSession.setUserSessionActive(user,false);

                        Platform.exit();
                        System.exit(0);
                    }
                });

                stage.show();

            } catch(IOException e) {
                e.printStackTrace();
            }

        }


    }

    @FXML
    private void handleRegisterButton(){

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/RegisterWindow.fxml"));

            Parent root = (Parent)loader.load();

            Stage stage = new Stage();
            Scene primaryScene = new Scene(root);
            primaryScene.getStylesheets().add("stylesheet.css");
            stage.setTitle("Rejestracja użytkownika");
            stage.setScene(primaryScene);
            stage.setResizable(false);

            stage.show();

        } catch(IOException e) {
            e.printStackTrace();
        }

    }
}
