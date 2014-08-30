package io.ppillai.spring.redis.repository;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import io.ppillai.spring.redis.repository.model.UserProfile;
import io.ppillai.spring.redis.repository.model.UserProfileRequest;
/**
 * Redis based user repository in spring
 * @author ppillai
 *
 */
@Repository
public class RedisUserRepository implements UserRepository {
	private static final String REGISTERED_USER_REPOSITORY = "registeredUserRepository";
	private static final String REGISTERED_USERS_CACHE = "registeredUsers";
	private static final Logger LOG = LoggerFactory.getLogger(RedisUserRepository.class);
	
	@Resource(name="jedisCacheTemplate")
	private RedisTemplate<String, UserProfile> redisTemplate;
	
	@Override
	@Cacheable(value = { REGISTERED_USERS_CACHE },key="#req.id",condition="#req!=null && #req.id!=null",unless="#result==null")
	public UserProfile getUser(UserProfileRequest req) {
		UserProfile userProfile = null;
		
		if (null!=req && StringUtils.isNoneBlank(req.getId())){
			userProfile = (UserProfile)redisTemplate.opsForHash().get(REGISTERED_USER_REPOSITORY, req.getId());
			LOG.info("Processed request for {}", userProfile.getId());
		}

		return userProfile;
	}

	@Override
	@CacheEvict(value={REGISTERED_USERS_CACHE},key="#req.id",condition="#req!=null && #req.id!=null",beforeInvocation=false)
	public void removeUser(UserProfileRequest req){
		LOG.info("Removing UserProfile {}", req.getId());
	}
	
	@Override
	@CacheEvict(value={REGISTERED_USERS_CACHE},allEntries=true)
	public void removeAllUsers(){
		LOG.info("Removing all UserProfile[s]");
	}

	@Override
	public UserProfileRequest addUser(UserProfile user) {
		UserProfileRequest req=null;
		
		if (null!=user && StringUtils.isNoneBlank(user.getId())){
			redisTemplate.opsForHash().put(REGISTERED_USER_REPOSITORY, user.getId(), user);
			req=new UserProfileRequest();
			req.setId(user.getId());
		}
		
		return req;
	}
	
}
