package us.infinz.pawelcwieka.organiser.resource;

public class ForecastDaily extends ForecastBase{

    private Double temperatureHigh;
    private Double temperatureLow;

    public Double getTemperatureHigh() {
        return temperatureHigh;
    }

    public void setTemperatureHigh(Double temperatureHigh) {
        this.temperatureHigh = temperatureHigh;
    }

    public Double getTemperatureLow() {

        return temperatureLow;
    }

    public void setTemperatureLow(Double temperatureLow) {
        this.temperatureLow = temperatureLow;
    }


}
