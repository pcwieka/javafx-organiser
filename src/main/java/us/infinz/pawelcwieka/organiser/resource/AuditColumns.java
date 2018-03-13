package us.infinz.pawelcwieka.organiser.resource;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class AuditColumns {

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_CREATED", nullable = true)
    private Date dateCreated;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_UPDATED", nullable = true)
    private Date dateUpdated;

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}
