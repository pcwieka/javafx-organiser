package us.infinz.pawelcwieka.organiser.resource;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class ForecastBase extends AuditColumns{

    @Column(name = "FORECAST_ICON")
    private String icon;

    @Column(name = "FORECAST_PRESSURE")
    private Double pressure;

    @Column(name = "FORECAST_WINDSPEED")
    private Double windSpeed;

    @Column(name = "FORECAST_CLOUDCOVER")
    private Double cloudCover;

    @Column(name = "FORECAST_WINDBEARING")
    private Double windBearing;

    @Column(name = "FORECAST_TIME")
    private Long time;

    @Column(name = "FORECAST_SUMMARY")
    private String summary;


}
