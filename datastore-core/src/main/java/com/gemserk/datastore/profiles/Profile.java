package com.gemserk.datastore.profiles;

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

	public Profile() {
	}

	public Profile(String name, boolean guest) {
		this.name = name;
		this.guest = guest;
	}

	public Profile(String privateKey, String publicKey, String name, boolean guest) {
		this.privateKey = privateKey;
		this.publicKey = publicKey;
		this.name = name;
		this.guest = guest;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((privateKey == null) ? 0 : privateKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Profile other = (Profile) obj;
		if (privateKey == null) {
			if (other.privateKey != null)
				return false;
		} else if (!privateKey.equals(other.privateKey))
			return false;
		return true;
	}

}
