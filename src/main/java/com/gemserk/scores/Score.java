package com.gemserk.scores;

import java.util.Map;
import java.util.Set;

public class Score {

	String id;

	String name;

	long points;

	long timestamp;

	Set<String> tags;

	Map<String, Object> data;

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

	public Score(String name, long points, Set<String> tags, Map<String, Object> data) {
		this.name = name;
		this.points = points;
		this.tags = tags;
		this.data = data;
	}

}
