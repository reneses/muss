package ie.cit.adf.muss.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Entity
public class Gamification {

	// --------------------------- Values ----------------------------
	
	public static final String TAG = "TAG";
	public static final String DESCRIPTION = "DESCRIPTION";
	public static final String REVIEW = "REVIEW";
	public static final String LIKEGIVEN = "LIKEGIVEN";
	public static final String LIKERECEIVED = "LIKERECEIVED";
	public static final String FOLLOWING = "FOLLOWING";
	public static final String FOLLOWERS = "FOLLOWERS";
	
	// ------------------------- Attributes --------------------------
	
	@Id
	@GeneratedValue
	private int id;
	
	@NotNull
	@Range(min = 0, max = 100)
	private Integer tagPoints, descriptionPoints, reviewPoints, likeGivenPoints, likeReceivedPoints, followingPoints, followersPoints;
	
	// ---------------------- Getters & Setters ----------------------
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Integer getTagPoints() {
		return tagPoints;
	}
	public void setTagPoints(Integer tagPoints) {
		this.tagPoints = tagPoints;
	}
	
	public Integer getDescriptionPoints() {
		return descriptionPoints;
	}
	public void setDescriptionPoints(Integer descriptionPoints) {
		this.descriptionPoints = descriptionPoints;
	}
	
	public Integer getReviewPoints() {
		return reviewPoints;
	}
	public void setReviewPoints(Integer reviewPoints) {
		this.reviewPoints = reviewPoints;
	}
	
	public Integer getLikeGivenPoints() {
		return likeGivenPoints;
	}
	public void setLikeGivenPoints(Integer likeGivenPoints) {
		this.likeGivenPoints = likeGivenPoints;
	}
	
	public Integer getLikeReceivedPoints() {
		return likeReceivedPoints;
	}
	public void setLikeReceivedPoints(Integer likeReceivedPoints) {
		this.likeReceivedPoints = likeReceivedPoints;
	}
	
	public Integer getFollowingPoints() {
		return followingPoints;
	}
	public void setFollowingPoints(Integer followingPoints) {
		this.followingPoints = followingPoints;
	}
	
	public Integer getFollowersPoints() {
		return followersPoints;
	}
	public void setFollowersPoints(Integer followersPoints) {
		this.followersPoints = followersPoints;
	}
	
	// -------------------------- Relations --------------------------
	
	// ------------------------ Quick methods ------------------------
	
}