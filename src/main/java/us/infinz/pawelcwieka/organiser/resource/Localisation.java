package us.infinz.pawelcwieka.organiser.resource;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "LOCALISATION")
public class Localisation extends DatabaseEntity{

    @Column(name = "LOCALISATION_FORMATTED_ADDRESS")
    private String formattedAddress;

    @Column(name = "LOCALISATION_USER_TYPED_NAME")
    private String userTypedName;

    @Column(name = "LOCALISATION_LATITUDE")
    private String latitude;

    @Column(name = "LOCALISATION_LONGITUDE")
    private String longitude;

    @Column(name = "LOCALISATION_ACTIVE")
    private Boolean active;

    public Localisation(){

    }

    public Localisation(String formattedAddress, String userTypedName, String latitude, String longitude, Boolean active) {

        this.formattedAddress = formattedAddress;
        this.userTypedName = userTypedName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.active = active;

    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public String getUserTypedName() {
        return userTypedName;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public Boolean getActive() {
        return active;
    }

    @Override
    public String toString(){

        return this.userTypedName;

    }
}
