package ie.cit.adf.muss.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Participant's role in an object
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Role {

	@JsonIgnore
	private int id;

	@JsonProperty("role_id")
	private int originalId;

	@JsonProperty("role_name")
	private String name;

	@JsonProperty("role_display_name")
	private String displayName;

	@JsonProperty("role_date")
	private String date;

	@JsonProperty("role_url")
	private String url;


    /* GETTERS AND SETTERS */

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

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}