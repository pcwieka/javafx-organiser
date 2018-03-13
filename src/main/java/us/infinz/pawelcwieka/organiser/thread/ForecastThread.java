package us.infinz.pawelcwieka.organiser.thread;

import javafx.beans.property.BooleanProperty;
import us.infinz.pawelcwieka.organiser.api.DarkSky;
import us.infinz.pawelcwieka.organiser.dao.IForecastDAO;
import us.infinz.pawelcwieka.organiser.dao.ForecastDAO;
import us.infinz.pawelcwieka.organiser.dao.ILocalisationDAO;
import us.infinz.pawelcwieka.organiser.dao.LocalisationDAO;
import us.infinz.pawelcwieka.organiser.resource.Forecast;
import us.infinz.pawelcwieka.organiser.resource.Localization;
import us.infinz.pawelcwieka.organiser.resource.User;
import us.infinz.pawelcwieka.organiser.util.Configuration;

public class ForecastThread extends Thread{

    private BooleanProperty booleanProperty;
    private User user;

    public ForecastThread(User user, BooleanProperty booleanProperty){

        this.booleanProperty = booleanProperty;
        this.user = user;

    }

        @Override
        public void run() {

            while (true){
                try {


                    ILocalisationDAO localisationDAO = new LocalisationDAO();

                    Localization localization = localisationDAO.findActiveLocalisation(user);

                    if(localization !=null){

                        DarkSky darkSky = new DarkSky();

                        Forecast forecast = darkSky.getForecast(localization);

                        localization.setForecast(forecast);

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
