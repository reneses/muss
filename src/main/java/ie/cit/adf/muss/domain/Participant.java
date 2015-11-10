package ie.cit.adf.muss.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Participant {
	
	@JsonProperty("person_id")
	private int id;
	@JsonProperty("person_name")
	private String name;
	@JsonProperty("person_date")
	private String date;
	@JsonProperty("person_url")
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

}
