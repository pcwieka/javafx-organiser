package us.infinz.pawelcwieka.organiser;
	

import java.io.IOException;
import java.util.Locale;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import us.infinz.pawelcwieka.organiser.thread.ForecastThread;

public class CalendarOrganiserMain extends Application {

	
	@Override
	public void start(Stage primaryStage) {

		showWindow("CalendarOrganiserMainWindow.fxml","Organizer", true, primaryStage, CalendarOrganiserMain.class);

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				Platform.exit();
				System.exit(0);
			}
		});


	}


	public static void main(String[] args) {
		launch(args);
	}


	public static void showWindow(String resource, String title, boolean resizable, Stage stage, Class cl){

		try {
			FXMLLoader loader = new FXMLLoader(cl.getResource("/fxmls/" + resource));

			Parent root = (Parent)loader.load();

			if(stage == null){

				stage = new Stage();

			}

			Scene primaryScene = new Scene(root);
			primaryScene.getStylesheets().add("stylesheet.css");
			stage.setTitle(title);
			stage.setScene(primaryScene);
			stage.setResizable(resizable);

			stage.show();

		} catch(IOException e) {
			e.printStackTrace();
		}


	}

	

}
