package us.infinz.pawelcwieka.organiser.dao;

import us.infinz.pawelcwieka.organiser.resource.Forecast;

import java.util.List;

public class ForecastDAOImpl implements ForecastDAO {

    private Database<Forecast> database = new DatabaseImpl<>();

    @Override
    public List<Forecast> findAllForecasts() {

        String query = "from FORECAST ";

        return database.getListOfObjectsFromDatabase(query);

    }

    @Override
    public Long saveForecast(Forecast forecast) {

        return database.saveObjectToDatabase(forecast);
    }

    @Override
    public void deleteAllForecasts() {

        String query = "delete FORECAST ";

        database.createCustomQuery(query);

    }
}
