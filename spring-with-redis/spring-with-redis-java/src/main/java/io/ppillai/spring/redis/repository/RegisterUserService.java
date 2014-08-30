package io.ppillai.spring.redis.repository;

import io.ppillai.spring.redis.repository.model.UserProfile;

public interface RegisterUserService {
	public void registerUser(UserProfile user);
}
