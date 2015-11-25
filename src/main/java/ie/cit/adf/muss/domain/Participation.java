package ie.cit.adf.muss.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.domain.Participant;
import ie.cit.adf.muss.domain.Role;

/**
 * Participation
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Participation {

	@JsonIgnore
	private int id;

	@JsonIgnore
	private ChObject object;

	@JsonUnwrapped
	private Participant participant;

	@JsonUnwrapped
	private Role role;

	public Participation() {
	}

	public ChObject getObject() {
		return object;
	}

	public void setObject(ChObject object) {
		if (this.object != null)
			throw new IllegalArgumentException("Once set, the object cannot be changed");
		this.object = object;
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
