package io.ppillai.spring.redis.repository;

import io.ppillai.spring.redis.repository.model.UserProfile;
import io.ppillai.spring.redis.repository.model.UserProfileRequest;

public interface UserRepository {
	public UserProfile getUser(UserProfileRequest req);
	public UserProfileRequest addUser(UserProfile user);
	public void removeUser(UserProfileRequest req);
	public void removeAllUsers();
}
