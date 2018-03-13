package us.infinz.pawelcwieka.organiser.resource;

import javax.persistence.*;
import java.util.List;

@Entity(name = "FORECAST")
public class Forecast extends ForecastBase{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "FORECAST_ID", updatable = false, nullable = false, unique=true)
    protected Long id;

    @Column(name = "FORECAST_SUMMARY_WEEK")
    private String summaryWeek;

    @Column(name = "FORECAST_TEMPERATURE")
    private Double temperature;

    @OneToOne(mappedBy = "forecast")
    private Localization localization;

    @Transient
    private List<ForecastDaily> nextWeekForecast;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Localization getLocalization() {
        return localization;
    }

    public void setLocalization(Localization localization) {
        this.localization = localization;
    }
}
