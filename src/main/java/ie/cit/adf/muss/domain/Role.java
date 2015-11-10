package ie.cit.adf.muss.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Role {
	
	@JsonProperty("role_id")
	private int id;
	@JsonProperty("role_name")
	private String name;
	@JsonProperty("role_display_name")
	private String displayName;
	@JsonProperty("role_url")
	private String url;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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
