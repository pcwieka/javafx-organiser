package us.infinz.pawelcwieka.organiser.resource;

import javax.persistence.*;

@MappedSuperclass
public abstract class DatabaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false, unique=true)
    protected Long id;

    public Long getId() {
        return id;
    }


}
