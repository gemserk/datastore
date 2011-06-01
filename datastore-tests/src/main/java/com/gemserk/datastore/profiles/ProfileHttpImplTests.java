package com.gemserk.datastore.profiles;

import java.text.MessageFormat;


public class ProfileHttpImplTests {

	public static void main(String[] args) {
		test1();
	}

	private static void test1() {
		ProfilesHttpImpl profiles = new ProfilesHttpImpl("http://localhost:8080/");
		Profile profile = profiles.register("guest-123141", true);
		System.out.println(MessageFormat.format("{0}, {1}, {2}, {3}", profile.getPrivateKey(), profile.getPublicKey(), profile.getName(), profile.isGuest()));
	}
	
}
