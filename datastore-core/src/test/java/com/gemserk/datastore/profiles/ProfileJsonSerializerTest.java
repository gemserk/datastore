package com.gemserk.datastore.profiles;

import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.junit.Test;


public class ProfileJsonSerializerTest {
	
	@Test
	public void testParseProfile() {
		ProfileJsonSerializer profileJsonSerializer = new ProfileJsonSerializer();
		Profile profile = profileJsonSerializer.parse("{\"privateKey\":\"57f47437-bcac-4c37-b352-c64105f02af4\",\"publicKey\":\"d34babe2-1852-4927-a388-4901ce4e3744\",\"name\":\"arielsan\",\"guest\":false}");
		
		assertThat(profile.getPrivateKey(), IsEqual.equalTo("57f47437-bcac-4c37-b352-c64105f02af4"));
		assertThat(profile.getPublicKey(), IsEqual.equalTo("d34babe2-1852-4927-a388-4901ce4e3744"));
		assertThat(profile.getName(), IsEqual.equalTo("arielsan"));
		assertThat(profile.isGuest(), IsEqual.equalTo(false));
	}
	
	@Test
	public void testParseProfileList() {
		ProfileJsonSerializer profileJsonSerializer = new ProfileJsonSerializer();
		Set<Profile> profileList = profileJsonSerializer.parseList("[{\"privateKey\":\"57f47437-bcac-4c37-b352-c64105f02af4\",\"publicKey\":\"d34babe2-1852-4927-a388-4901ce4e3744\",\"name\":\"arielsan\",\"guest\":false}, {\"privateKey\":\"57f47437-bcad-4c37-b352-c64105f02af4\",\"publicKey\":\"d34babe2-1852-4927-a388-4901ce4e3744\",\"name\":\"arielsan\",\"guest\":false}]");

		assertThat(profileList, IsNull.notNullValue());
		assertThat(profileList.size(), IsEqual.equalTo(2));
	}
	
	@Test
	public void testSerializeProfileList() {
		ProfileJsonSerializer profileJsonSerializer = new ProfileJsonSerializer();
		
		Set<Profile> profileList = new HashSet<Profile>();
		
		profileList.add(new Profile("PRIVATE1", "PUBLIC1", "abc", true));
		profileList.add(new Profile("PRIVATE2", "PUBLIC2", "abc2", false));
		
		System.out.println(profileJsonSerializer.serialize(profileList));
	}

}
