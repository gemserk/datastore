package com.gemserk.datastore.profiles;

public interface Profiles {

	/**
	 * Registers a profile with specified name and returns it.
	 * 
	 * @param name
	 *            The name of the profile.
	 * @param guest
	 *            Defines if the profile should be guest or not.
	 * @return
	 */
	Profile register(String name, boolean guest);

	/**
	 * Updates the profile's name if profile was guest, it fails otherwise.
	 * 
	 * @param profile
	 */
	Profile update(Profile profile);

}
