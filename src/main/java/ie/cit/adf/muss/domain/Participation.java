package ie.cit.adf.muss.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

@Entity
public class Participation {
	
	@JsonBackReference
	private ChObject chObject;
	@JsonUnwrapped
	private Participant participant;
	@JsonUnwrapped
	private Role role;
	
	@ManyToOne(optional=false)
	public ChObject getChObject() {
		return chObject;
	}
	
	public void setChObject(ChObject chObject) {
		this.chObject = chObject;
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
