package us.infinz.pawelcwieka.organiser.resource;

import javax.persistence.*;
import java.util.Date;

//@MappedSuperclass
public abstract class AuditColumns {

    //@Temporal(TemporalType.TIME)
    //@Column(name = "DATE_CREATED", nullable = true)
    private Date dateCreated;

    //@Temporal(TemporalType.TIME)
    //@Column(name = "DATE_UPDATED", nullable = true)
    private Date dateUpdated;


    public Date getDateCreated() {
        return dateCreated;
    }

    //@PrePersist
    public void setDateCreated() {
        this.dateCreated = new Date();
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    //@PreUpdate
    public void setDateUpdated() {
        this.dateUpdated = new Date();
    }

    public abstract Long getId();
}
