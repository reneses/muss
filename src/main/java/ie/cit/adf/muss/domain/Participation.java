package ie.cit.adf.muss.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.domain.Participant;
import ie.cit.adf.muss.domain.Role;

/**
 * Participation
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Participation {

	@Id
	@GeneratedValue
	@JsonIgnore
	private int id;

	@JsonIgnore
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="chobject_id")
	private ChObject chObject;

	@JsonUnwrapped
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="participant_id")
	private Participant participant;

	@JsonUnwrapped
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="role_id")
	private Role role;

	public Participation() {
		
	}

	public ChObject getChObject() {
		return chObject;
	}

	public void setChObject(ChObject chObject) {
		this.chObject = chObject;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Participant getParticipant() {
		return participant;
	}

	public void setParticipant(Participant participant) {
		this.participant = participant;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
