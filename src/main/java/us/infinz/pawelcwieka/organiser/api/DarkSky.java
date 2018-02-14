package us.infinz.pawelcwieka.organiser.api;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import us.infinz.pawelcwieka.organiser.resource.Forecast;
import us.infinz.pawelcwieka.organiser.resource.ForecastDaily;
import us.infinz.pawelcwieka.organiser.resource.Localisation;
import us.infinz.pawelcwieka.organiser.service.MessageWindowProvider;

import java.util.ArrayList;
import java.util.List;

public class DarkSky {

    private static final String _APIKEY = "418fba598d0e757ba3a48d03e90f4737";
    private static final String _BASE_URL = "https://api.darksky.net/forecast/";

    public Forecast getForecast(Localisation localisation){

        String latitude = localisation.getLatitude();
        String longitude = localisation.getLongitude();


        HttpResponse<JsonNode> httpResponse = null;
        Forecast forecast = null;

        try {

            httpResponse = Unirest.get(_BASE_URL + _APIKEY + "/"+ latitude + "," + longitude+ "?lang=pl&units=si&exclude=hourly,minutely,flags").asJson();

            JSONObject jsonObject = httpResponse.getBody().getObject();

            forecast = getForecastFromJSONObject(jsonObject);

        } catch (UnirestException e) {

            MessageWindowProvider messageWindowProvider = new MessageWindowProvider(

                    "Błąd z połączeniem sieciowym",
                    "Nie można pobrać prognozy pogody dla podanej lokalizacji. \n" +
                            "Sprawdź połączenie sieciowe."
            );

            messageWindowProvider.showMessageWindow();

            e.printStackTrace();

        }

        return forecast;

    }

    private Forecast getForecastFromJSONObject(JSONObject jsonObject) {

        Forecast forecast = new Forecast();

        JSONObject jsonForecast = jsonObject.getJSONObject("currently");

        forecast.setTime(jsonForecast.getLong("time"));
        forecast.setSummary(jsonForecast.getString("summary"));
        forecast.setIcon(jsonForecast.getString("icon"));
        forecast.setTemperature(jsonForecast.getDouble("temperature"));
        forecast.setPressure(jsonForecast.getDouble("pressure"));
        forecast.setWindSpeed(jsonForecast.getDouble("windSpeed"));
        forecast.setCloudCover(jsonForecast.getDouble("cloudCover"));
        forecast.setWindBearing(jsonForecast.getDouble("windBearing"));

        JSONObject jsonDaily = jsonObject.getJSONObject("daily");

        forecast.setSummaryWeek(jsonDaily.getString("summary"));


        JSONArray jsonForecastDaily = jsonDaily
                .getJSONArray("data");

        List<ForecastDaily> forecastDailyList = new ArrayList<>();

        for(int i = 0; i < jsonForecastDaily.length();i++){

            ForecastDaily forecastDaily = new ForecastDaily();

            JSONObject daily = jsonForecastDaily.getJSONObject(i);

            forecastDaily.setTime(daily.getLong("time"));
            forecastDaily.setSummary(daily.getString("summary"));
            forecastDaily.setIcon(daily.getString("icon"));
            forecastDaily.setPressure(daily.getDouble("pressure"));
            forecastDaily.setWindBearing(daily.getDouble("windSpeed"));
            forecastDaily.setCloudCover(daily.getDouble("cloudCover"));
            forecastDaily.setWindBearing(daily.getDouble("windBearing"));
            forecastDaily.setTemperatureHigh(daily.getDouble("temperatureHigh"));
            forecastDaily.setTemperatureLow(daily.getDouble("temperatureLow"));

            forecastDailyList.add(forecastDaily);

        }

        forecast.setNextWeekForecast(forecastDailyList);

        return forecast;

    }

}
