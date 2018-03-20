package us.infinz.pawelcwieka.organiser.dao;

import us.infinz.pawelcwieka.organiser.resource.Forecast;
import us.infinz.pawelcwieka.organiser.resource.Localization;
import us.infinz.pawelcwieka.organiser.resource.User;

import java.util.List;

public class ForecastDAO implements IForecastDAO {

    private IDatabase<Forecast> database = new Database<>(Forecast.class);

    @Override
    public Forecast findForecast(Localization localization) {

        String query = "f from FORECAST f WHERE f.";

        return database.createCustomQueryGet(query);

    }

    @Override
    public Long saveForecast(Forecast forecast) {

        return database.saveObjectToDatabase(forecast);
    }

    @Override
    public void deleteAllForecasts() {

        String query = "delete FORECAST ";

        database.createCustomQueryUpdate(query);

    }
}
