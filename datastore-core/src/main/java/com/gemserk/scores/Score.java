package com.gemserk.scores;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;

public class Score {

	String id;

	String profilePublicKey;

	String name;

	long points;

	long timestamp;

	Set<String> tags;

	Map<String, Object> data;

	public String getProfilePublicKey() {
		return profilePublicKey;
	}

	public void setProfilePublicKey(String profilePublicKey) {
		this.profilePublicKey = profilePublicKey;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	public void setData(Map<String, Object> values) {
		this.data = values;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public Set<String> getTags() {
		return tags;
	}

	public long getPoints() {
		return points;
	}

	public String getName() {
		return name;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPoints(long points) {
		this.points = points;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public Score() {

	}

	public Score(String profilePublicKey, String name, long points, Set<String> tags, Map<String, Object> data) {
		this.profilePublicKey = profilePublicKey;
		this.name = name;
		this.points = points;
		this.tags = tags;
		this.data = data;
	}

	@Override
	public String toString() {
		return MessageFormat.format("SCORE [id:{0}, profilePublicKey:{5}, name:{1}, points:{2}, tags:{3}, data:{4}]", id, name, points, tags, data, profilePublicKey);
	}

}
