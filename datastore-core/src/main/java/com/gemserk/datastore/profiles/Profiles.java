package com.gemserk.datastore.profiles;

/**
 * Provides a way to create profiles and update them.
 */
public interface Profiles {

	/**
	 * Registers a profile with specified name and returns it.
	 * 
	 * @param name
	 *            The name of the profile.
	 * @param guest
	 *            Defines if the profile should be guest or not.
	 * @return The a new Profile with that name.
	 */
	Profile register(String name, boolean guest);

	/**
	 * Updates the profile's name if profile was guest, it fails otherwise.
	 * 
	 * @param profile
	 *            The profile to be updated.
	 */
	Profile update(Profile profile);

	/**
	 * Returns a Profile with all its information given a profilePrivateKey.
	 * 
	 * @param profilePrivateKey
	 *            The Profile private key identifying it.
	 * @return The Profile identified by the private key.
	 */
	Profile getProfile(String profilePrivateKey);

}
