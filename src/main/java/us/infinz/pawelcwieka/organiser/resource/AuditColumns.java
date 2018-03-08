package us.infinz.pawelcwieka.organiser.resource;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@MappedSuperclass
public class AuditColumns {

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_CREATED", nullable = false)
    private Date dateCreated;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_UPDATED", nullable = false)
    private Date dateUpdated;

    @PrePersist
    protected void onCreate() {
        dateUpdated = dateCreated = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        dateUpdated = new Date();
    }
}
