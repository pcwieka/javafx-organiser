package us.infinz.pawelcwieka.organiser.service;

import javafx.beans.property.BooleanProperty;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.joda.time.DateTime;
import us.infinz.pawelcwieka.organiser.api.DarkSky;
import us.infinz.pawelcwieka.organiser.dao.IForecastDAO;
import us.infinz.pawelcwieka.organiser.dao.ForecastDAO;
import us.infinz.pawelcwieka.organiser.dao.ILocalisationDAO;
import us.infinz.pawelcwieka.organiser.dao.LocalisationDAO;
import us.infinz.pawelcwieka.organiser.resource.Forecast;
import us.infinz.pawelcwieka.organiser.resource.Localization;
import us.infinz.pawelcwieka.organiser.resource.User;

import java.util.List;

public class WeatherBoxCreator {


    private VBox weatherTopVBox;
    private VBox weatherBottomVBox;
    private ImageView weatherIcon;
    private BooleanProperty booleanProperty;

    private User user;

    public WeatherBoxCreator(VBox weatherTopVBox, VBox weatherBottomVBox, ImageView weatherIcon,BooleanProperty booleanProperty) {

        this.weatherTopVBox = weatherTopVBox;
        this.weatherBottomVBox = weatherBottomVBox;
        this.weatherIcon = weatherIcon;
        this.booleanProperty = booleanProperty;

    }

    public void createWeatherVBox() {

        weatherTopVBox.getChildren().clear();

        ILocalisationDAO localisationDAO = new LocalisationDAO();
        Localization localization = localisationDAO.findActiveLocalisation(user);

        if(localization != null){

        DarkSky darkSky = new DarkSky();
        Forecast forecast = darkSky.getForecast(localization);

        Forecast prevoiusForecast = localization.getForecast();

        Long forecastId = null;

        if(prevoiusForecast != null){

                forecastId = prevoiusForecast.getId();
                forecast.setId(forecastId);

        }

        localization.setForecast(forecast);


            DateTime time = new DateTime(forecast.getTime() * 1000);

            Label localisationLabel = new Label(localization.getFormattedAddress() + ", "
                    + time.dayOfMonth().getAsText() + " " + time.monthOfYear().getAsText() + ", "
                    + (time.hourOfDay().get() < 10 ? ("0" + time.hourOfDay().getAsText()) : time.hourOfDay().getAsText())
                    + ":" + (time.minuteOfHour().get() < 10 ? "0" + time.minuteOfHour().getAsText() : time.minuteOfHour().getAsText()));

            localisationLabel.getStyleClass().add("label-weather-place");

            Label summaryLabel = new Label(forecast.getSummary());
            summaryLabel.getStyleClass().add("label-weather-summary");
            Label summaryWeekLabel = new Label(forecast.getSummaryWeek());
            summaryWeekLabel.getStyleClass().add("label-weather");


            Double windBearing = forecast.getWindBearing();
            String windDirection = convertDegreeToCardinalDirection(windBearing.intValue());

            Label tempWindSpeedLabel = new Label("Temperatura: " + forecast.getTemperature() + " st.C, Wiatr: " + forecast.getWindSpeed() + " m/s, (" + windDirection + ")");
            tempWindSpeedLabel.getStyleClass().add("label-weather");
            Label pressureCloudCoverLabel = new Label("Ciśnienie: " + forecast.getPressure() + " hPa, Zachmurzenie: " + (forecast.getCloudCover().intValue() * 100) + "%");
            pressureCloudCoverLabel.getStyleClass().add("label-weather");

            weatherTopVBox.getChildren().addAll(localisationLabel, summaryLabel, summaryWeekLabel, tempWindSpeedLabel, pressureCloudCoverLabel);

            Image image = null;

            if (!forecast.getIcon().equals("sleet")) {

                image = new Image("/icons/weather/" + forecast.getIcon() + ".png");

            } else {

                image = new Image("/icons/weather/rain.png");

            }

            weatherIcon.setImage(image);

            weatherIcon.setPickOnBounds(true);

            weatherIcon.setOnMouseClicked((MouseEvent e) -> {

                ILocalisationDAO lDAO = new LocalisationDAO();

                Localization loc = lDAO.findActiveLocalisation(user);

                if (loc != null) {

                    Forecast forcst = darkSky.getForecast(loc);

                    Forecast prevForecast = loc.getForecast();

                    Long forId = null;

                    if(prevForecast != null){

                        forId = prevForecast.getId();
                        forcst.setId(forId);

                    }

                    loc.setForecast(forcst);

                    booleanProperty.set(!booleanProperty.get());

                    MessageWindowProvider messageWindowProvider = new MessageWindowProvider(

                            "Uwaga!",
                            "Aktualizacja prognozy pogody zakończyła się pomyślnie."
                    );

                    messageWindowProvider.showMessageWindow();

                }

            });


        }

    }

    public String convertDegreeToCardinalDirection(int directionInDegrees){
        String cardinalDirection = null;
        if( (directionInDegrees >= 348.75) && (directionInDegrees <= 360) ||
                (directionInDegrees >= 0) && (directionInDegrees <= 11.25)    ){
            cardinalDirection = "N";
        } else if( (directionInDegrees >= 11.25 ) && (directionInDegrees <= 33.75)){
            cardinalDirection = "NNE";
        } else if( (directionInDegrees >= 33.75 ) &&(directionInDegrees <= 56.25)){
            cardinalDirection = "NE";
        } else if( (directionInDegrees >= 56.25 ) && (directionInDegrees <= 78.75)){
            cardinalDirection = "ENE";
        } else if( (directionInDegrees >= 78.75 ) && (directionInDegrees <= 101.25) ){
            cardinalDirection = "E";
        } else if( (directionInDegrees >= 101.25) && (directionInDegrees <= 123.75) ){
            cardinalDirection = "ESE";
        } else if( (directionInDegrees >= 123.75) && (directionInDegrees <= 146.25) ){
            cardinalDirection = "SE";
        } else if( (directionInDegrees >= 146.25) && (directionInDegrees <= 168.75) ){
            cardinalDirection = "SSE";
        } else if( (directionInDegrees >= 168.75) && (directionInDegrees <= 191.25) ){
            cardinalDirection = "S";
        } else if( (directionInDegrees >= 191.25) && (directionInDegrees <= 213.75) ){
            cardinalDirection = "SSW";
        } else if( (directionInDegrees >= 213.75) && (directionInDegrees <= 236.25) ){
            cardinalDirection = "SW";
        } else if( (directionInDegrees >= 236.25) && (directionInDegrees <= 258.75) ){
            cardinalDirection = "WSW";
        } else if( (directionInDegrees >= 258.75) && (directionInDegrees <= 281.25) ){
            cardinalDirection = "W";
        } else if( (directionInDegrees >= 281.25) && (directionInDegrees <= 303.75) ){
            cardinalDirection = "WNW";
        } else if( (directionInDegrees >= 303.75) && (directionInDegrees <= 326.25) ){
            cardinalDirection = "NW";
        } else if( (directionInDegrees >= 326.25) && (directionInDegrees <= 348.75) ){
            cardinalDirection = "NNW";
        } else {
            cardinalDirection = "?";
        }

        return cardinalDirection;
    }


    public void setUser(User user) {
        this.user = user;
    }
}
