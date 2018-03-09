package us.infinz.pawelcwieka.organiser.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@RequiredArgsConstructor
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
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    private Forecast forecast;

    @Override
    public String toString(){

        return this.userTypedName;

    }
}
