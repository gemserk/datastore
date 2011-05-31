package com.gemserk.scores;

public class Profile {

	String privateKey;

	String publicKey;

	String name;

	boolean guest;

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String id) {
		this.privateKey = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isGuest() {
		return guest;
	}

	public void setGuest(boolean guest) {
		this.guest = guest;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	
}
