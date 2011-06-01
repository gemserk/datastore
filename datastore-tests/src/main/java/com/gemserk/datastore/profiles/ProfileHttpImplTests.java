package com.gemserk.datastore.profiles;

import java.text.MessageFormat;


public class ProfileHttpImplTests {

	public static void main(String[] args) {
		test2();
	}

	private static void test1() {
		ProfilesHttpImpl profiles = new ProfilesHttpImpl("http://localhost:8080/");
		Profile profile = profiles.register("guest-123141", true);
		System.out.println(MessageFormat.format("{0}, {1}, {2}, {3}", profile.getPrivateKey(), profile.getPublicKey(), profile.getName(), profile.isGuest()));
	}
	
	private static void test2() {
		ProfilesHttpImpl profiles = new ProfilesHttpImpl("http://localhost:8080/");
		
		Profile profile = profiles.register("guest-123141", true);
		profile.setName("arielsan");
		
		profile = profiles.update(profile);
		
		System.out.println(MessageFormat.format("{0}, {1}, {2}, {3}", profile.getPrivateKey(), profile.getPublicKey(), profile.getName(), profile.isGuest()));
	}
	
}
