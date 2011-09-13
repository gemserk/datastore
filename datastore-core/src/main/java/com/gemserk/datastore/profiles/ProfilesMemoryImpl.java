package com.gemserk.datastore.profiles;

import java.util.HashMap;
import java.util.Random;

public class ProfilesMemoryImpl implements Profiles {
	
	private HashMap<String, Profile> profileMap = new HashMap<String, Profile>();
	private Random random = new Random();

	@Override
	public Profile register(String name, boolean guest) {
		Profile profile = new Profile(generatePrivateKey(), generatePublicKey(), name, guest);
		profileMap.put(profile.getPrivateKey(), profile);
		return profile;
	}

	private String generatePublicKey() {
		return "privateKey-" + getRandomNumber();
	}

	private String generatePrivateKey() {
		return "publicKey-" + getRandomNumber();
	}

	private long getRandomNumber() {
		return (random.nextInt() % 89999) + 10000;
	}

	@Override
	public Profile update(Profile profile) {
		if (!profile.isGuest()) 
			throw new RuntimeException("can't update a non guest profile");
		
		Profile savedProfile = profileMap.get(profile.getPrivateKey());
		
		savedProfile.setName(profile.getName());
		savedProfile.setGuest(false);
		
		return savedProfile;
	}
	
	public Profile getProfile(String profilePrivateKey) {
		return profileMap.get(profilePrivateKey);
	}

}
