package us.infinz.pawelcwieka.organiser.resource;


import javax.persistence.*;
import java.util.Set;

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

	@ManyToMany(mappedBy = "events", cascade = {CascadeType.ALL})
	private Set<User> users;

	@PreRemove
	private void removeGroupsFromUsers() {
		for (User u : users) {
			u.getEvents().remove(this);
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public Long getStartHour() {
		return startHour;
	}

	public void setStartHour(Long startHour) {
		this.startHour = startHour;
	}

	public Long getEndHour() {
		return endHour;
	}

	public void setEndHour(Long endHour) {
		this.endHour = endHour;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
}
