package us.infinz.pawelcwieka.organiser.resource;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Double getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(Double cloudCover) {
        this.cloudCover = cloudCover;
    }

    public Double getWindBearing() {
        return windBearing;
    }

    public void setWindBearing(Double windBearing) {
        this.windBearing = windBearing;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
