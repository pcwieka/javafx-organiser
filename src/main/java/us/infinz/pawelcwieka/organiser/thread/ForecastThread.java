package us.infinz.pawelcwieka.organiser.thread;

import javafx.beans.property.BooleanProperty;
import us.infinz.pawelcwieka.organiser.api.DarkSky;
import us.infinz.pawelcwieka.organiser.dao.ForecastDAO;
import us.infinz.pawelcwieka.organiser.dao.ForecastDAOImpl;
import us.infinz.pawelcwieka.organiser.dao.LocalisationDAO;
import us.infinz.pawelcwieka.organiser.dao.LocalisationDAOImpl;
import us.infinz.pawelcwieka.organiser.resource.Forecast;
import us.infinz.pawelcwieka.organiser.resource.Localization;
import us.infinz.pawelcwieka.organiser.util.Configuration;

public class ForecastThread extends Thread{

    private BooleanProperty booleanProperty;

    public ForecastThread(BooleanProperty booleanProperty){

        this.booleanProperty = booleanProperty;

    }

        @Override
        public void run() {

            while (true){
                try {


                    LocalisationDAO localisationDAO = new LocalisationDAOImpl();

                    Localization localization = localisationDAO.findActiveLocalisation();

                    if(localization !=null){

                        DarkSky darkSky = new DarkSky();

                        Forecast forecast = darkSky.getForecast(localization);

                        ForecastDAO forecastDAO = new ForecastDAOImpl();
                        forecastDAO.deleteAllForecasts();
                        forecastDAO.saveForecast(forecast);

                        booleanProperty.set(!booleanProperty.get());

                        System.out.println("Odświeżono pogodę.");

                    }

                    Thread.sleep(Configuration.getProperty("forecast") == null ? 5*60*1000 : Long.parseLong(Configuration.getProperty("forecast"))*60*1000);


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

    }
}
