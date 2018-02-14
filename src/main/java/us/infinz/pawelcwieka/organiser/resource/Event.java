package us.infinz.pawelcwieka.organiser.resource;

import javax.persistence.*;

@Entity(name = "EVENT")
public class Event extends DatabaseEntity{

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

	public Event(){

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
}
