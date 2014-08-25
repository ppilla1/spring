package io.ppillai.spring.redis.repository.model;

import java.io.Serializable;

public class UserProfileRequest implements Serializable {
	/**
	 * UserProfileRequest serialUID for JDK 1.7
	 */
	private static final long serialVersionUID = -6159768120649387356L;
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
