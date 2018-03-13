package us.infinz.pawelcwieka.organiser.resource;

import javax.persistence.*;

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

    @OneToOne(mappedBy = "configuration")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConfigurationForecastRefresh() {
        return configurationForecastRefresh;
    }

    public void setConfigurationForecastRefresh(String configurationForecastRefresh) {
        this.configurationForecastRefresh = configurationForecastRefresh;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
