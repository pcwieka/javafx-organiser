package us.infinz.pawelcwieka.organiser.resource;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name = "EVENT")
public class Event extends AuditColumns{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EVENT_ID", updatable = false, nullable = false, unique=true)
	protected Long id;

	@Column(name = "EVENT_DATE")
	private Long date;

	@Column(name = "EVENT_STARTHOUR")
	private Long startHour;

	@Column(name = "EVENT_ENDHOUR")
	private Long endHour;

	@Column(name = "EVENT_LABEL")
	private String label;

	@Column(name = "EVENT_DESCRIPTION")
	private String description;

	@Column(name = "EVENT_PLACE")
	private String place;

	@ManyToMany(mappedBy = "events")
	private Set<User> users = new HashSet<>();

}
