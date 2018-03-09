package us.infinz.pawelcwieka.organiser.resource;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity(name = "CONFIGURATION")
public class Configuration extends AuditColumns{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CONFIGURATION_ID",
            updatable = false,
            nullable = false,
            unique=true
    )
    private Long id;

    @Column(name = "CONFIGURATION_FORECAST_REFRESH")
    private String configurationForecastRefresh;

}
