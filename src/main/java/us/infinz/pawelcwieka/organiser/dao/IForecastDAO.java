package us.infinz.pawelcwieka.organiser.dao;

import us.infinz.pawelcwieka.organiser.resource.Forecast;
import us.infinz.pawelcwieka.organiser.resource.Localization;
import us.infinz.pawelcwieka.organiser.resource.User;

import java.util.List;

public interface IForecastDAO {

    public Forecast findForecast(Localization localization);
    public Long saveForecast(Forecast forecast);
    public void deleteAllForecasts();
}
