package ie.cit.adf.muss.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Participant
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Participant {

	@Id
	@GeneratedValue
	@JsonIgnore
	private int id;

	@JsonProperty("person_id")
	private int originalId;

	@JsonProperty("person_name")
	private String name;

	@JsonProperty("person_date")
	private String date;

	@JsonProperty("person_url")
	private String url;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="participant", cascade=CascadeType.ALL)
	@JsonIgnore
	private Collection<Participation> participations;
	
	public Participant() {
		participations = new ArrayList<>();
	}


    /* GETTERS AND SETTERS */

	public Collection<Participation> getParticipations() {
		return participations;
	}

	public void setParticipations(Collection<Participation> participations) {
		this.participations = participations;
		participations.forEach(p -> {
			if (!this.equals(p.getParticipant()))
				p.setParticipant(this);
		});
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOriginalId() {
		return originalId;
	}

	public void setOriginalId(int originalId) {
		this.originalId = originalId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
    public boolean equals(Object obj) {
        return obj instanceof Participant && id > 0 && id == ((Participant) obj).getId();
    }

    @Override
    public int hashCode() {
        return id * name.hashCode();
    }

}