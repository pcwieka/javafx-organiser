package us.infinz.pawelcwieka.organiser.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MessageWindowController implements Initializable{

    @FXML
    private Label titleLabel;
    @FXML
    private Text messageText;
    @FXML
    private Button okButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }


    @FXML
    private void handleOkButton(){

        Stage stage = (Stage)okButton.getScene().getWindow();
        stage.close();

    }

    public void setTitleAndMessage(String title, String message){

        titleLabel.setText(title);
        messageText.setText(message);
        messageText.getStyleClass().add("label-weather");

    }
}
