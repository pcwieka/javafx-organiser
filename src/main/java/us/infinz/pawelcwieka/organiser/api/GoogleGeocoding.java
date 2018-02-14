package us.infinz.pawelcwieka.organiser.api;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import us.infinz.pawelcwieka.organiser.resource.Localisation;
import us.infinz.pawelcwieka.organiser.exception.GoogleGeolocationException;
import us.infinz.pawelcwieka.organiser.service.MessageWindowProvider;

public class GoogleGeocoding {

    private static final String _APIKEY = "AIzaSyDPGrhxEKA9WPD7n7-GPCvhtv6QNxoOCLY";
    private static final String _BASE_URL = "https://maps.googleapis.com/maps/api/geocode/json?address=";

    private String userTypedPlaceName;

    public GoogleGeocoding(String userTypedPlaceName) {

        this.userTypedPlaceName = userTypedPlaceName;

    }

    public Localisation getLocalisationDetails() {

        //Unirest.setObjectMapper(new JacksonObjectMapperAdapter());

        HttpResponse<JsonNode> httpResponse = null;
        Localisation localisation = null;

        try {

            httpResponse = Unirest.get(_BASE_URL + this.userTypedPlaceName.replace(" ", "+").trim() + "&key=" + _APIKEY).asJson();

            JSONObject jsonObject = httpResponse.getBody().getObject();

            String geocodingStatus = getGeolocationStatus(jsonObject);

            if (!geocodingStatus.equals("OK")) {

                throw new GoogleGeolocationException("Google Geocoding place status: " + geocodingStatus);

            }

            localisation = getPlaceFromJSONObject(jsonObject);

        } catch (UnirestException e) {

            MessageWindowProvider messageWindowProvider = new MessageWindowProvider(

                    "Błąd z połączeniem sieciowym",
                    "Nie można pobrać lokalizacji. Sprawdź połączenie sieciowe."
            );

            messageWindowProvider.showMessageWindow();

            e.printStackTrace();

        } catch (GoogleGeolocationException e) {

            MessageWindowProvider messageWindowProvider = new MessageWindowProvider(

                    "Błąd lokalizacji",
                    "Nie można pobrać żądanej lokalizacji. \n" +
                            "Wprowadź poprawne dane w ustawieniach."
            );

            messageWindowProvider.showMessageWindow();

            e.printStackTrace();
        }

        return localisation;


    }

    private Localisation getPlaceFromJSONObject(JSONObject jsonObject) {

        JSONObject jsonLocation = jsonObject.getJSONArray("results")
                .getJSONObject(0)
                .getJSONObject("geometry")
                .getJSONObject("location");

        String latitude = String.valueOf(jsonLocation.getDouble("lat"));
        String longitude = String.valueOf(jsonLocation.getDouble("lng"));
        String formattedAddress = jsonObject.getJSONArray("results")
                .getJSONObject(0)
                .getString("formatted_address");

        return new Localisation(formattedAddress, this.userTypedPlaceName, latitude, longitude, true);

    }

    private String getGeolocationStatus(JSONObject jsonObject) {

        return jsonObject.getString("status");


    }


}
