package us.infinz.pawelcwieka.organiser.resource;

import lombok.*;

import javax.persistence.*;

@Entity(name = "LOCALIZATION")
public class Localization extends AuditColumns{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LOCALIZATION_ID", updatable = false, nullable = false, unique=true)
    protected Long id;

    @Column(name = "LOCALIZATION_FORMATTED_ADDRESS")
    @NonNull
    private String formattedAddress;

    @Column(name = "LOCALIZATION_USER_TYPED_NAME")
    @NonNull
    private String userTypedName;

    @Column(name = "LOCALIZATION_LATITUDE")
    @NonNull
    private String latitude;

    @Column(name = "LOCALIZATION_LONGITUDE")
    @NonNull
    private String longitude;

    @Column(name = "LOCALIZATION_ACTIVE")
    @NonNull
    private Boolean localizationActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FORECAST_ID")
    private Forecast forecast;

    public Localization(){

    }

    public Localization(String formattedAddress, String userTypedName, String latitude, String longitude, Boolean localizationActive) {
        this.formattedAddress = formattedAddress;
        this.userTypedName = userTypedName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.localizationActive = localizationActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getUserTypedName() {
        return userTypedName;
    }

    public void setUserTypedName(String userTypedName) {
        this.userTypedName = userTypedName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Boolean getLocalizationActive() {
        return localizationActive;
    }

    public void setLocalizationActive(Boolean active) {
        this.localizationActive = active;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Forecast getForecast() {
        return forecast;
    }

    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }

    @Override
    public String toString(){

        return this.userTypedName;

    }
}
