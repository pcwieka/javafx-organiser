package us.infinz.pawelcwieka.organiser.controller;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.joda.time.DateTime;
import us.infinz.pawelcwieka.organiser.api.Email;
import us.infinz.pawelcwieka.organiser.dao.EventDAOImpl;
import us.infinz.pawelcwieka.organiser.exception.EmailException;
import us.infinz.pawelcwieka.organiser.service.CalendarCreator;
import us.infinz.pawelcwieka.organiser.dao.DatabaseImpl;
import us.infinz.pawelcwieka.organiser.resource.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import us.infinz.pawelcwieka.organiser.dao.EventDAO;
import us.infinz.pawelcwieka.organiser.service.Clock;
import us.infinz.pawelcwieka.organiser.service.ICalendar;
import us.infinz.pawelcwieka.organiser.service.MessageWindowProvider;
import us.infinz.pawelcwieka.organiser.util.Configuration;


public class DayEventWindowController implements Initializable{

	@FXML
	private Label dayLabel;
	@FXML
	private TextField labelTextField;
	@FXML
	private TextField placeTextField;
	@FXML
	private ComboBox startHourComboBox;
	@FXML
	private ComboBox startMinutesComboBox;
	@FXML
	private ComboBox endHourComboBox;
	@FXML
	private ComboBox endMinutesComboBox;
	@FXML
	private TextArea detailsTexField;
	@FXML
	private ImageView iCalendarImageView;
	@FXML
	private ImageView googleCalendarImageView;
	@FXML
	private Button googleCalendarButton;
	@FXML
	private Button iCalendarButton;
	@FXML
	private Button saveButton;
	@FXML
	private Button quitButton;
	@FXML
	private Button deleteButton;
	
	
	private DateTime date;
	private Event event;
	private EventDAO eventDAO;
	private CalendarCreator calendarCreator;
	private Long eventId;

	private ObservableList<String> hourOptions;
	private ObservableList<String> minuteOptions;

	

	@Override
    public void initialize(URL url, ResourceBundle rb) {

		eventDAO = new EventDAOImpl();
		calendarCreator = CalendarCreator.getInstance();
		
		addTextLimiter(labelTextField,10);
		addTextLimiter(detailsTexField,500);

		hourOptions = FXCollections.observableArrayList();

		for(int i = 0; i <=23; i++){

			hourOptions.add(String.valueOf(i));

		}

		minuteOptions = FXCollections.observableArrayList();

		for(int i = 1; i <=59; i++){

			minuteOptions.add(String.valueOf(i));

		}

		setComboBoxItemsRightTextAlignment(startHourComboBox);
		setComboBoxItemsRightTextAlignment(endHourComboBox);
		setComboBoxItemsLeftTextAlignment(startMinutesComboBox);
		setComboBoxItemsLeftTextAlignment(endMinutesComboBox);

		startHourComboBox.getItems().addAll(hourOptions);
		endHourComboBox.getItems().addAll(hourOptions);
		startMinutesComboBox.getItems().addAll(minuteOptions);
		endMinutesComboBox.getItems().addAll(minuteOptions);


		Image iCalImag = new Image("/icons/ical.png");
		iCalendarImageView.setImage(iCalImag);

		Image gCalImg = new Image("/icons/googleCal.png");
		googleCalendarImageView.setImage(gCalImg);



	}

	
	@FXML
	private void handleSaveButton() {
		
		setEventFields();
		
		eventDAO.saveEvent(event);
		calendarCreator.createCalendar();
		
		Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close(); 
		
		
	}
	
	
	
	@FXML
	private void handleDeleteButton(){
		
		eventDAO.deleteEvent(event);
		calendarCreator.createCalendar();
		
		Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
		
		
	}
	
	
	
	@FXML
	private void handleQuitButton() {
		
		Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
		
		
	}

	@FXML
	private void handleICalendarButton() {

		setEventFields();

		try {

			InputStream inputStream = ICalendar.createICalendarEvent(
					new Date(event.getStartHour()),
					new Date(event.getEndHour()),
					event.getLabel(),
					event.getPlace(),
					event.getDescription());


			FileChooser fileChooser = new FileChooser();
			//Set extension filter
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("ICS files (*.ics)", "*.ics");

			fileChooser.getExtensionFilters().add(extFilter);

			Stage stage = (Stage) quitButton.getScene().getWindow();
			//Show save file dialog
			File file = fileChooser.showSaveDialog(stage);

			OutputStream outputStream = new FileOutputStream(file);

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}



		}catch(IOException e){

			e.printStackTrace();

		}


	}

	@FXML
	private void handleGoogleCalendarButton(){

		try {

			String emailString = Configuration.getProperty("email");

			if(emailString.isEmpty()){

				throw new EmailException();

			}

			setEventFields();

			InputStream inputStream = ICalendar.createICalendarEvent(
					new Date(event.getStartHour()),
					new Date(event.getEndHour()),
					event.getLabel(),
					event.getPlace(),
					event.getDescription());

			Email email = new Email(Configuration.getProperty("email"), event.getLabel(), event.getLabel(), "", inputStream );

			email.send();


		}catch(IOException e){

			MessageWindowProvider messageWindowProvider = new MessageWindowProvider(

					"Błąd podczas zapisu do pliku.",
					"Nie można zapisać pliku. Spróbuj ponownie lub skontaktuj się z Administratorem."
			);

			messageWindowProvider.showMessageWindow();

		e.printStackTrace();

		}catch (EmailException e){

			MessageWindowProvider messageWindowProvider = new MessageWindowProvider(

					"Brak adresu email",
					"Wprowadź poprawny adres email w ustawieniach i spróbuj ponownie."
			);

			messageWindowProvider.showMessageWindow();


		}


	}
	
	private void setEventFields(){
		
		Long startHour = Clock.getTimeStampInMilis(date, (String) startHourComboBox.getValue(), (String) startMinutesComboBox.getValue());
		Long endHour = Clock.getTimeStampInMilis(date, (String) endHourComboBox.getValue(), (String) endMinutesComboBox.getValue());

		if(eventId == null){

			event.setDate(date.getMillis());

		}
		
		event.setStartHour(startHour);
		event.setEndHour(endHour);
		event.setLabel(labelTextField.getText());
		event.setPlace(placeTextField.getText());
		event.setDescription(detailsTexField.getText());
			
		
	}
	
	private void setDayLabelText(){
		
		dayLabel.setText(date.dayOfWeek().getAsText().toUpperCase()+", "+ date.dayOfMonth().getAsString()+" "+date.monthOfYear().getAsText()+" "+date.getYear());
		
	}

	public void init(DateTime date, Long eventId){

		this.eventId = eventId;
		this.date = date;

		setDayLabelText();

		if(eventId != null){

			event = getEventById(eventId);

			labelTextField.setText(event.getLabel());
			placeTextField.setText(event.getPlace());
			detailsTexField.setText(event.getDescription());

			startHourComboBox.setValue(Clock.getHourFromDateTime(event.getStartHour()));
			endHourComboBox.setValue(Clock.getHourFromDateTime(event.getEndHour()));
			startMinutesComboBox.setValue(Clock.getMinutesFromDateTime(event.getStartHour()));
			endMinutesComboBox.setValue(Clock.getMinutesFromDateTime(event.getEndHour()));

			deleteButton.setDisable(false);

		} else {

			event = new Event();

			startHourComboBox.setValue("11");
			endHourComboBox.setValue("11");
			startMinutesComboBox.setValue("0");
			endMinutesComboBox.setValue("0");

		}


	}

	private Event getEventById(Long eventId){

		return eventDAO.findEvent(eventId);

	}
	
	
	private void addTextLimiter(final TextField tf, final int maxLength) {
		
	    tf.textProperty().addListener((observable, oldValue, newValue) -> {if (tf.getText().length() > maxLength) {
	                String s = tf.getText().substring(0, maxLength);
	                tf.setText(s);
	            }
	        }
	    );
	}
	
	private void addTextLimiter(final TextArea ta, final int maxLength) {
		
	    ta.textProperty().addListener((observable, oldValue, newValue) -> {if (ta.getText().length() > maxLength) {
	                String s = ta.getText().substring(0, maxLength);
	                ta.setText(s);
	            }
	        }
	    );
	}
	
	 

	 public EventHandler<KeyEvent> numeric_Validation(final Integer max_Lengh) {
	        return new EventHandler<KeyEvent>() {
	            @Override
	            public void handle(KeyEvent e) {
	                TextField txt_TextField = (TextField) e.getSource();
	                if (txt_TextField.getText().length() >= max_Lengh) {
	                    e.consume();
	                }
	                if(e.getCharacter().matches("[.]")){
	                    e.consume();
	                }

	                if (e.getCharacter().matches("[0-9.]")) {
	                    if (txt_TextField.getText().contains(".") && e.getCharacter().matches("[.]")) {
	                        e.consume();
	                    } else if (txt_TextField.getText().length() == 0 && e.getCharacter().matches("[.]")) {
	                        e.consume();
	                    }
	                } else {
	                    e.consume();
	                }
	            }
	        };

	    }

	    private void setComboBoxItemsRightTextAlignment(ComboBox comboBox){


			comboBox.setButtonCell(new ListCell<String>() {
				@Override
				public void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (item != null) {
						setText(item);
						setAlignment(Pos.CENTER_RIGHT);
						Insets old = getPadding();
						setPadding(new Insets(old.getTop(), 0, old.getBottom(), 0));
					}
				}
			});

		}

	private void setComboBoxItemsLeftTextAlignment(ComboBox comboBox){


		comboBox.setButtonCell(new ListCell<String>() {
			@Override
			public void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (item != null) {
					setText(item);
					setAlignment(Pos.CENTER);
					Insets old = getPadding();
					setPadding(new Insets(old.getTop(), 0, old.getBottom(), 0));
				}
			}
		});

	}
	
}
