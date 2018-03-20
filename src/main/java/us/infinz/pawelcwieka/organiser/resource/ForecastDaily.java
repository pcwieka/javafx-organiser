package us.infinz.pawelcwieka.organiser.resource;

import lombok.Data;

@Data
public class ForecastDaily extends ForecastBase{

    private Double temperatureHigh;
    private Double temperatureLow;

    @Override
    public Long getId() {
        return null;
    }
}
