package us.infinz.pawelcwieka.organiser.resource;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Data
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

    @Transient
    private List<ForecastDaily> nextWeekForecast;


}
