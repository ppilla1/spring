package io.ppillai.spring.redis.repository;

import static org.junit.Assert.*;
import io.ppillai.spring.redis.repository.model.UserProfile;
import io.ppillai.spring.redis.repository.model.UserProfileRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-with-redis-java-ctx.xml")
public class IntegrationTestUserRepository {
	private static final Logger LOG = LoggerFactory.getLogger(IntegrationTestUserRepository.class);
	
	@Autowired
	private UserRepository repository;

	private String getJSON(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		String json=null;
		
		try {
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			LOG.warn("Exception occured : ", e);
		}
		
		return json;
	}
	
	@Test
	public void get_user(){
		UserProfileRequest req = new UserProfileRequest();
		req.setId("ppillai");
		UserProfile user = repository.getUser(req);
		assertNotNull(user);
		LOG.info("UserProfile : {}", getJSON(user));
		
		req = new UserProfileRequest();
		req.setId("ppillai");
		user = repository.getUser(req);
		assertNotNull(user);
		LOG.info("UserProfile : {}", getJSON(user));		
	}

	@Test
	public void get_user_for_NullOrEmptyOrNonExistingUserId(){
		UserProfileRequest req = null;
		UserProfile user = repository.getUser(req);
		assertNull(user);

		req = new UserProfileRequest();
		user = repository.getUser(req);
		assertNull(user);
		
		req = new UserProfileRequest();
		req.setId("");
		user = repository.getUser(req);
		assertNull(user);

		req = new UserProfileRequest();
		req.setId("ksomal");
		user = repository.getUser(req);
		assertNull(user);

		
	}
}
