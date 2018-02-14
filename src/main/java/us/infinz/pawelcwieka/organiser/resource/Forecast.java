package us.infinz.pawelcwieka.organiser.resource;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.List;

@Entity(name = "FORECAST")
public class Forecast extends ForecastBase{

    @Column(name = "FORECAST_SUMMARY_WEEK")
    private String summaryWeek;
    @Column(name = "FORECAST_TEMPERATURE")
    private Double temperature;
    @Transient
    private List<ForecastDaily> nextWeekForecast;

    public Forecast(){

    }

    public String getSummaryWeek() {
        return summaryWeek;
    }

    public void setSummaryWeek(String summaryWeek) {
        this.summaryWeek = summaryWeek;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public List<ForecastDaily> getNextWeekForecast() {
        return nextWeekForecast;
    }

    public void setNextWeekForecast(List<ForecastDaily> nextWeekForecast) {
        this.nextWeekForecast = nextWeekForecast;
    }



}
