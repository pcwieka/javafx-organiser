package us.infinz.pawelcwieka.organiser.service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import us.infinz.pawelcwieka.organiser.controller.MessageWindowController;

import java.io.IOException;

public class MessageWindowProvider {

    private String title;
    private String message;

    public MessageWindowProvider(String title, String message) {
        this.title = title;
        this.message = message;

    }

    public void showMessageWindow(){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/MessageWindow.fxml"));

            Parent root = (Parent)loader.load();

            MessageWindowController messageWindowController = loader.getController();

            messageWindowController.setTitleAndMessage(title,message);

            Stage stage = new Stage();
            Scene primaryScene = new Scene(root);
            primaryScene.getStylesheets().add("stylesheet.css");
            stage.setTitle("Warning");
            stage.setScene(primaryScene);
            stage.setResizable(false);

            stage.show();

        } catch(IOException e) {
            e.printStackTrace();
        }



    }
}
