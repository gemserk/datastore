package com.gemserk.datastore.profiles;

import com.google.gson.Gson;

public class ProfileJsonSerializer {

	Gson gson = new Gson();

	public Profile parse(String profileJson) {
		return gson.fromJson(profileJson, Profile.class);
	}

	public String serialize(Profile profile) {
		return gson.toJson(profile);
	}

}