package com.gemserk.datastore.profiles;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProfilesFileImpl implements Profiles {

	protected static final Logger logger = LoggerFactory.getLogger(ProfilesFileImpl.class);

	private final Random random = new Random();
	private final File storage;
	private final ProfileJsonSerializer profileJsonSerializer;
	
	public ProfilesFileImpl(File storage) {
		this.storage = storage;
		this.profileJsonSerializer = new ProfileJsonSerializer();
	}
	
	String getFileContent() throws IOException {
		return FileUtils.readFileToString(storage);
	}

	void writeFileContent(String value) throws IOException {
		FileUtils.writeStringToFile(storage, value);
	}
	
	void ensureFileExists() throws IOException {
		if (!storage.exists())
			writeFileContent("[]");
	}

	@Override
	public Profile register(String name, boolean guest) {
		Profile profile = new Profile(generatePrivateKey(), generatePublicKey(), name, guest);
		
		Set<Profile> profiles = getProfiles();
		profiles.add(profile);
		updateProfiles(profiles);
		
		return profile;
	}

	private String generatePrivateKey() {
		return "privateKey-" + getRandomNumber();
	}

	private String generatePublicKey() {
		return "publicKey-" + getRandomNumber();
	}

	private long getRandomNumber() {
		return (random.nextInt() % 89999) + 10000;
	}

	@Override
	public Profile update(Profile profile) {
		if (!profile.isGuest()) 
			throw new RuntimeException("can't update a non guest profile");
		
		profile.setGuest(false);
		
		Set<Profile> profiles = getProfiles();
		profiles.add(profile);
		updateProfiles(profiles);
		return profile;
	}

	@Override
	public Profile getProfile(String profilePrivateKey) {
		Set<Profile> profileSet = getProfiles();
		for (Profile profile : profileSet) {
			if (profile.getPrivateKey().equals(profilePrivateKey))
				return profile;
		}
		return null;
	}
	
	private Set<Profile> getProfiles() {
		try {
			ensureFileExists();
			String readData = getFileContent();
			Set<Profile> profiles = profileJsonSerializer.parseList(readData);
			return profiles;
		} catch (IOException e) {
			throw new RuntimeException("couldn't read storage: " + storage, e);
		}
	}
	
	private void updateProfiles(Set<Profile> profiles) {
		try {
			String serializedProfiles = profileJsonSerializer.serialize(profiles);
			writeFileContent(serializedProfiles);
		} catch (IOException e) {
			throw new RuntimeException("couldn't write to storage: " + storage, e);
		}
	}

}
