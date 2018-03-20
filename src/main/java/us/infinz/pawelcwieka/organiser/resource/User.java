package us.infinz.pawelcwieka.organiser.resource;

import lombok.Data;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity(name = "USER")
public class User extends AuditColumns{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID",
            updatable = false,
            nullable = false,
            unique=true
    )
    private Long id;

    @Column(name = "USER_LOGIN")
    private String userLogin;

    @Column(name = "USER_PASSWORD")
    private String userPassword;

    @Column(name = "USER_EMAIL")
    private String userEmail;

    @Column(name = "USER_SESSION_ACTIVE")
    private Boolean sessionActive;

    @Column(name = "USER_AUTHORIZATION")
    private Boolean authorization;

    @ManyToMany(fetch = FetchType.LAZY,cascade = { CascadeType.ALL})
    @JoinTable(
            name = "SHARED_EVENTS",
            joinColumns = { @JoinColumn(name = "USER_ID") },
            inverseJoinColumns = { @JoinColumn(name = "EVENT_ID") }
    )
    Set<Event> events;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Localization> localizations;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CONFIGURATION_ID")
    private Configuration configuration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Boolean getSessionActive() {
        return sessionActive;
    }

    public void setSessionActive(Boolean sessionActive) {
        this.sessionActive = sessionActive;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public Set<Localization> getLocalizations() {
        return localizations;
    }

    public void setLocalizations(Set<Localization> localizations) {
        this.localizations = localizations;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public Boolean getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Boolean authorization) {
        this.authorization = authorization;
    }
}
