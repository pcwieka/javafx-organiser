package us.infinz.pawelcwieka.organiser.resource;

import lombok.Data;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
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

    @Column(name = "USER_DATE_CREATED")
    private String userDateCreated;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "SHARED_EVENTS",
            joinColumns = { @JoinColumn(name = "USER_ID") },
            inverseJoinColumns = { @JoinColumn(name = "EVENT_ID") }
    )
    Set<Event> events = new HashSet<>();


}
