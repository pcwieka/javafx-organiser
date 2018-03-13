package us.infinz.pawelcwieka.organiser.controller;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.joda.time.DateTime;
import us.infinz.pawelcwieka.organiser.resource.User;
import us.infinz.pawelcwieka.organiser.service.CalendarCreator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;


public class CalendarOrganiserMainWindowController implements Initializable {
	
	@FXML
    private GridPane calendarGridPane;
	@FXML
	private ImageView settingIcon;
	@FXML
	private VBox weatherTopVBox;
	@FXML
	private VBox weatherBottomVBox;
	@FXML
	private ImageView weatherIcon;
	@FXML
	private Button prevMonthButton;
	@FXML
	private Button nextMonthButton;
	@FXML
	private Button showCurrentMonth;
	@FXML
	private Button settingsButton;
	@FXML
	private Label currentMonthLabel;

	private DateTime pickedDay;

	private CalendarCreator calendarCreator = CalendarCreator.getInstance();

	private User user;
	
	
	@Override
    public void initialize(URL url, ResourceBundle rb) {

		pickedDay = new DateTime();

		calendarCreator.setPickedDay(pickedDay);

		currentMonthLabel.setText(pickedDay.monthOfYear().getAsText().toUpperCase()+" "+ pickedDay.getYear());
		currentMonthLabel.getStyleClass().add("label-day-window");

		Image settingImg = new Image("/icons/settings.png");
		settingIcon.setImage(settingImg);

				calendarCreator.setCurrentMonthLabel(currentMonthLabel);
				calendarCreator.setCalendarGridPane(calendarGridPane);
				calendarCreator.setWeatherBottomVBox(weatherBottomVBox);
				calendarCreator.setWeatherTopVBox(weatherTopVBox);
				calendarCreator.setWeatherIcon(weatherIcon);
				calendarCreator.createCalendar();



	}

	@FXML
	private void handlePrevMonthButton(){

		pickedDay = pickedDay.minusMonths(1);

		currentMonthLabel.setText(pickedDay.monthOfYear().getAsText().toUpperCase()+" "+ pickedDay.getYear());

		calendarCreator.setPickedDay(pickedDay);

		calendarCreator.createCalendar();


	}

	@FXML
	private void handleNextMonthButton(){

		pickedDay = pickedDay.plusMonths(1);

		currentMonthLabel.setText(pickedDay.monthOfYear().getAsText().toUpperCase()+" "+ pickedDay.getYear());

		calendarCreator.setPickedDay(pickedDay);

		calendarCreator.createCalendar();




	}


	@FXML
	private void handleShowCurrentMonth(){

		pickedDay = new DateTime();

		currentMonthLabel.setText(pickedDay.monthOfYear().getAsText().toUpperCase()+" "+ pickedDay.getYear());

		calendarCreator.setPickedDay(pickedDay);

		calendarCreator.createCalendar();


	}

	@FXML
	private void handleSettingsButton(){

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/SettingsWindow.fxml"));

			Parent root = (Parent)loader.load();

			SettingsWindowController settingsWindowController = loader.getController();
			settingsWindowController.init(user);

			Stage stage = new Stage();
			Scene primaryScene = new Scene(root);
			primaryScene.getStylesheets().add("stylesheet.css");
			stage.setTitle("Ustawienia");
			stage.setScene(primaryScene);
			stage.setResizable(false);

			stage.show();

		} catch(IOException e) {
			e.printStackTrace();
		}

	}

	public void setParams(User user){

		this.user = user;

	}


}
