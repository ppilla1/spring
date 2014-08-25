package io.ppillai.spring.redis.repository.model;

import java.io.Serializable;

public class UserProfile implements Serializable{
	/**
	 * UserProfile serialUID for JDK 1.7
	 */
	private static final long serialVersionUID = -1502297616775350660L;
	private String id;
	private String firstName;
	private String lastName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
