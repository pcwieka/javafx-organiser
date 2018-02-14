package us.infinz.pawelcwieka.organiser.dao;

import us.infinz.pawelcwieka.organiser.resource.Forecast;
import java.util.List;

public interface ForecastDAO {

    public List<Forecast> findAllForecasts();
    public Long saveForecast(Forecast forecast);
    public void deleteAllForecasts();
}
