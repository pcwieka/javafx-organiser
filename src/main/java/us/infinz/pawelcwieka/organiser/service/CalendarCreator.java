package us.infinz.pawelcwieka.organiser.service;

import java.io.IOException;
import java.util.*;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.hibernate.Hibernate;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import us.infinz.pawelcwieka.organiser.controller.DayEventWindowController;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import us.infinz.pawelcwieka.organiser.dao.UserDAO;
import us.infinz.pawelcwieka.organiser.resource.Event;
import us.infinz.pawelcwieka.organiser.resource.User;
import us.infinz.pawelcwieka.organiser.thread.ForecastThread;


public class CalendarCreator {
	
	
private static CalendarCreator instance = null;
	
	
	public static CalendarCreator getInstance(){
        
        if(instance ==null){
            
            instance = new CalendarCreator();
            
        }
        
        return instance;
        
    }
	
	private GridPane calendarGridPane;
	private VBox weatherTopVBox;
	private VBox weatherBottomVBox;
	private ImageView weatherIcon;
	private Label currentMonthLabel;

	private DateTime pickedDay;
	private DateTime today;
	
	private String[] dayNames = {
			
			"Poniedziałek",
			"Wtorek",
			"Środa",
			"Czwartek",
			"Piątek",
			"Sobota",
			"Niedziela"
		
	};

	private static final DateTimeFormatter hoursFormatter = DateTimeFormat.forPattern("HH:mm");
	
	private DateTime startDay;
	private DateTime startDayConst;
	private DateTime dateTimeMinusDays;
	private int maxRowNumber;
	private boolean newRowAdded=false;
	private Set<Event> allUserEvents;

	private BooleanProperty booleanProperty = new SimpleBooleanProperty(true);

	private User user;

	public CalendarCreator(){

		booleanProperty.addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				System.out.println("changed " + oldValue + "->" + newValue);

				WeatherBoxCreator weatherBoxCreator = new WeatherBoxCreator(weatherTopVBox,weatherBottomVBox,weatherIcon,booleanProperty);
				weatherBoxCreator.setUser(user);

				Platform.runLater(() ->

						weatherBoxCreator.createWeatherVBox()

				);


			}
		});


	}
	

	public void createCalendar(){

		today = new DateTime();
		maxRowNumber = 5;

		if(newRowAdded){

			calendarGridPane.getRowConstraints().remove(maxRowNumber+1);
			newRowAdded=false;

		}
		
		
		calendarGridPane.getChildren().clear();

		System.out.println("USERNAME: " + user.getUserLogin() + ", USER_ID: " + user.getId());

		UserDAO userDAO = new UserDAO();

		allUserEvents = userDAO.findUser(user.getId()).getEvents();

		createAllDayCellsAndEvents();
		addDayNamesRowsAnTopAndBottom();
		addWeeksNumbersCells();
		calendarGridPane.getStyleClass().add("grid");

		WeatherBoxCreator weatherBoxCreator = new WeatherBoxCreator(weatherTopVBox,weatherBottomVBox,weatherIcon,booleanProperty);
		weatherBoxCreator.setUser(user);
		weatherBoxCreator.createWeatherVBox();
		
		
		}
		

	
	private void addDayNamesRowsAnTopAndBottom(){
		
		for(int col = 1 ; col < 8; col++){
			
			VBox vBoxTop = createDayNameVBox(0,col);
			VBox vBoxBottom = createDayNameVBox(maxRowNumber+1,col);
			
		    calendarGridPane.getChildren().addAll(vBoxTop,vBoxBottom);
		        
		        
			}
		
	
	}
	
	
	private VBox createDayNameVBox(int row, int col){
		
		VBox dayNameVBox = new VBox();
		dayNameVBox.getStyleClass().add("vbox-daynames");
		Label label = new Label(dayNames[col-1]);
		label.getStyleClass().add("label-daynames");
		
		dayNameVBox.getChildren().add(label);
		
		GridPane.setRowIndex(dayNameVBox, row);
		GridPane.setColumnIndex(dayNameVBox, col);
		
		return dayNameVBox;
		
		
	}
	
	
	private void addWeeksNumbersCells(){
		
		
		int weekOfTheYear = dateTimeMinusDays.getWeekOfWeekyear();
		
		
		for(int row = 1 ; row <= maxRowNumber; row++){
			
			
		
			VBox vBoxLeft = createWeekNumberVBox(row,0,weekOfTheYear);
			VBox vBoxRight = createWeekNumberVBox(row,8,weekOfTheYear);
			
		        calendarGridPane.getChildren().addAll(vBoxLeft,vBoxRight);
		        
		        weekOfTheYear++;
		        
			}
				
	}
	
	
	
	private VBox createWeekNumberVBox(int row, int col, int weekOfTheYear){
		
		VBox weekNumber = new VBox();
		weekNumber.getStyleClass().add("vbox-weeks");
		Label label = new Label("W "+weekOfTheYear+"\n"+ getPickedDay().getYear());
		label.getStyleClass().add("label-weeks");
		weekNumber.getChildren().add(label);
	
		GridPane.setRowIndex(weekNumber, row);
		GridPane.setColumnIndex(weekNumber, col);
		
		return weekNumber;
		
		
	}
	
	
	
	private void createAllDayCellsAndEvents(){

		int dayOfMonth = getPickedDay().getDayOfMonth();

		dateTimeMinusDays = getPickedDay().minusDays(dayOfMonth-1);
		
		int dayOfWeek = dateTimeMinusDays.getDayOfWeek();

		startDay = dateTimeMinusDays.minusDays(dayOfWeek-1);

		startDayConst = startDay;



		if((dayOfWeek>=6 && getMonthLength(getPickedDay())>30)||(dayOfWeek>6 && getMonthLength(getPickedDay())>=30)){

			newRowAdded=true;

			maxRowNumber=6;
			RowConstraints rowConstraints = new RowConstraints();
			rowConstraints.setFillHeight(true);
			rowConstraints.setVgrow(Priority.SOMETIMES);
			rowConstraints.setValignment(VPos.CENTER);
			rowConstraints.setMinHeight(10);
			rowConstraints.setPrefHeight(5);
			rowConstraints.setPercentHeight(-1);

			calendarGridPane.getRowConstraints().add(maxRowNumber,rowConstraints);


		}
		
		for(int row = 1 ; row <= maxRowNumber; row++){
			
			for(int col = 1 ; col < 8; col++){
				
				VBox dayCell = new VBox();
				HBox hBox = new HBox();

				Label topLabel = new Label(startDay.getDayOfMonth() +" " +startDay.monthOfYear().getAsText());

				if(startDay.toLocalDate().equals(today.toLocalDate())){

					dayCell.getStyleClass().add("vbox-day-today");
					hBox.getStyleClass().add("hbox-dayname-cell-today");
					topLabel.getStyleClass().add("label-dayname-cell-today");



				} else if((row==1 && startDay.getDayOfMonth()>=10)||(row==maxRowNumber && startDay.getDayOfMonth()<=10)){

					dayCell.getStyleClass().add("vbox-day-outOfMonthDay");
					hBox.getStyleClass().add("hbox-dayname-cell-outOfMonthDay");
					topLabel.getStyleClass().add("label-dayname-cell-outOfMonthDay");




				} else if(startDay.getDayOfWeek()==6){

					dayCell.getStyleClass().add("vbox-day-saturday");
					hBox.getStyleClass().add("hbox-dayname-cell-saturday");
					topLabel.getStyleClass().add("label-dayname-cell-saturday");

				} else if(startDay.getDayOfWeek()==7){

					dayCell.getStyleClass().add("vbox-day-sunday");
					hBox.getStyleClass().add("hbox-dayname-cell-sunday");
					topLabel.getStyleClass().add("label-dayname-cell-sunday");


				} else {

					dayCell.getStyleClass().add("vbox-day");
					hBox.getStyleClass().add("hbox-dayname-cell");
					topLabel.getStyleClass().add("label-dayname-cell");

				}

				GridPane.setColumnIndex(topLabel, col);
	            GridPane.setRowIndex(topLabel, row);
	            
	            hBox.getChildren().add(topLabel);
				
				
				dayCell.getChildren().add(hBox);
				setDayOnMouseClickBehaviour(topLabel);
				
				List<Event> eventsForTheDayList = getAllEventsForTheDay(startDay);
				
				Collections.sort(eventsForTheDayList,new Comparator<Event>() {
				    @Override
				    public int compare(Event a, Event b) {
				        return a.getStartHour().compareTo(b.getStartHour());
				    }
				});


				ScrollPane scrollPane = new ScrollPane();
				scrollPane.setFitToWidth(true);



				VBox eventsVBox = new VBox();
				eventsVBox.getStyleClass().add("vbox-events");
			
				
				for(Event event : eventsForTheDayList){
					
					HBox eventHBox = new HBox();
					eventHBox.getStyleClass().add("hbox-event");

					DateTime startHour = new DateTime(event.getStartHour());
					DateTime endHour = new DateTime(event.getEndHour());
					
					Label eventLabel = new Label(
							
							hoursFormatter.print(startHour)
							+"-"+ hoursFormatter.print(endHour)+" "+
							event.getLabel()
							
							);
					
					eventLabel.getStyleClass().add("label-event");
					eventLabel.setId(Long.toString(event.getId()));
					GridPane.setColumnIndex(eventLabel, col);
		            GridPane.setRowIndex(eventLabel, row);
		            setEventOnMouseClickBehaviour(eventLabel);
					
					eventHBox.getChildren().add(eventLabel);
					
					
					eventsVBox.getChildren().add(eventHBox);
					
				}

				scrollPane.setContent(eventsVBox);
				scrollPane.getStyleClass().add("scroll-pane");
				
				dayCell.getChildren().add(scrollPane);
				
				GridPane.setRowIndex(dayCell, row);
				GridPane.setColumnIndex(dayCell, col);

				
				calendarGridPane.getChildren().add(dayCell);
				
				startDay = startDay.plusDays(1);
				
				
			}
			
			
		}
		
		startDay = getPickedDay().minusDays(dayOfWeek-1);
		
	}
	
	
	
	private List<Event> getAllEventsForTheDay(DateTime day){
		
		List<Event> eventsForTheDayList = new ArrayList<Event>();
		
		for(Event event : allUserEvents){

			DateTime eventDay = Clock.getDateTimeFromTimeStamp(event.getDate());
			
			if(	eventDay.getDayOfMonth() == day.getDayOfMonth() &&
					eventDay.getMonthOfYear() == day.getMonthOfYear() &&
					eventDay.getYear() == day.getYear())
			{
				
				eventsForTheDayList.add(event);
				
			}
			
		}
		
		return eventsForTheDayList;
		
	}


	private int getMonthLength(DateTime date){

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, date.getMonthOfYear()-1);
		cal.set(Calendar.YEAR, date.getYear());

		 return cal.getActualMaximum(Calendar.DAY_OF_MONTH);

	}
	
	
	
	private void setDayOnMouseClickBehaviour(Label label){
		
		label.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent mouseEvent) {
		        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
		            if(mouseEvent.getClickCount() == 1){
		            	
		            	Label sourceLabel = (Label)mouseEvent.getSource() ;
		                Integer colIndex = GridPane.getColumnIndex(sourceLabel);
		                Integer rowIndex = GridPane.getRowIndex(sourceLabel);
		                int dayIndex = colIndex.intValue() + 7*(rowIndex.intValue() - 1);
		                DateTime selectedDay = startDayConst.plusDays(dayIndex-1);;
						
						openWindowOfDaySelected(selectedDay,null);
		                
		            }
		        }
		    }
		});

	}
	
	
	private void setEventOnMouseClickBehaviour(Label label){
		
		label.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent mouseEvent) {
		        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
		            if(mouseEvent.getClickCount() == 1){
		                
		            	Label sourceLabel = (Label)mouseEvent.getSource() ;
		                Integer colIndex = GridPane.getColumnIndex(sourceLabel);
		                Integer rowIndex = GridPane.getRowIndex(sourceLabel);
		                int dayIndex = colIndex.intValue() + 7*(rowIndex.intValue() - 1);
		                DateTime selectedDay = startDayConst.plusDays(dayIndex-1);
						
		                Long labelId = Long.parseLong(label.getId());
		                
						openWindowOfDaySelected(selectedDay,labelId);
		            	
		            	
		            	
		            	
		            }
		        }
		    }
		});
		
		
	}
	
	
	private void openWindowOfDaySelected(DateTime selectedDay, Long labelId){

		String stageTitle = null;



		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/DayEventWindow.fxml"));

			Parent root = (Parent)loader.load();

			DayEventWindowController dEWC = loader.getController();

			if(labelId==null){

				stageTitle = "Dodaj nowe wydarzenie";

			} else{

				stageTitle = "Edytuj wydarzenie";


			}

			dEWC.init(selectedDay,labelId, user);

			Stage stage = new Stage();
			Scene primaryScene = new Scene(root);
			primaryScene.getStylesheets().add("stylesheet.css");
			stage.setTitle(stageTitle);
			stage.setScene(primaryScene);
			stage.setResizable(false);

			stage.show();

		} catch(IOException e) {
			e.printStackTrace();
		}
		
		
	}

	public void startThread(){

		ForecastThread forecastThread = new ForecastThread(user,booleanProperty);
		forecastThread.start();

	}
	
	

	public GridPane getCalendarGridPane() {
		return calendarGridPane;
	}

	public boolean isBooleanProperty() {
		return booleanProperty.get();
	}

	public BooleanProperty booleanPropertyProperty() {
		return booleanProperty;
	}

	public void setBooleanProperty(boolean booleanProperty) {
		this.booleanProperty.set(booleanProperty);
	}

	public void setCalendarGridPane(GridPane calendarGridPane) {
		this.calendarGridPane = calendarGridPane;
	}


	public Label getCurrentMonthLabel() {
		return currentMonthLabel;
	}

	public void setCurrentMonthLabel(Label currentMonthLabel) {
		this.currentMonthLabel = currentMonthLabel;
	}

	public DateTime getPickedDay() {
		return pickedDay;
	}

	public void setPickedDay(DateTime pickedDay) {
		this.pickedDay = pickedDay;
	}

	public void setWeatherTopVBox(VBox weatherTopVBox) {
		this.weatherTopVBox = weatherTopVBox;
	}

	public void setWeatherBottomVBox(VBox weatherBottomVBox) {
		this.weatherBottomVBox = weatherBottomVBox;
	}

	public void setWeatherIcon(ImageView weatherIcon) {
		this.weatherIcon = weatherIcon;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
