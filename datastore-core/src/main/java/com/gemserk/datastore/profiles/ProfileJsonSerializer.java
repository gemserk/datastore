package com.gemserk.datastore.profiles;

import com.google.gson.Gson;

public class ProfileJsonSerializer {

	Gson gson = new Gson();

	public Profile parseProfile(String profileJson) {
		return gson.fromJson(profileJson, Profile.class);
	}

}