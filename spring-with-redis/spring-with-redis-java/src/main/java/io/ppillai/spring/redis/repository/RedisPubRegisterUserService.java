package io.ppillai.spring.redis.repository;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import io.ppillai.spring.redis.repository.model.UserProfile;

@Service
public class RedisPubRegisterUserService implements RegisterUserService {
	private static final String NEW_USERCHANNEL = "new.user";

	private static final Logger LOG = LoggerFactory.getLogger(RedisPubRegisterUserService.class);
	
	@Resource(name="jedisPubSubTemplate")
	private RedisTemplate<String, UserProfile> redisTemplate;
	
	@Override
	public void registerUser(UserProfile user) {
		redisTemplate.convertAndSend(NEW_USERCHANNEL, user);
	}

}
