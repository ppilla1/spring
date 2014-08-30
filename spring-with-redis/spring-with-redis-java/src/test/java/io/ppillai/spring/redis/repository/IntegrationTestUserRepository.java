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
	public void add_get_remove_deleteAllUsers(){
		UserProfile user = null;
		UserProfileRequest req = null;
		
		user = new UserProfile();
		user.setId("ppillai");
		user.setFirstName("Prashant");
		user.setLastName("Pillai");
		
		req=repository.addUser(user);
		assertNotNull(req);
		
		user=repository.getUser(req);
		assertNotNull(user);
		
		LOG.info("UserProfile : {}",getJSON(user));
		
		user=repository.getUser(req);
		assertNotNull(user);
		
		LOG.info("UserProfile : {}",getJSON(user));

		user = new UserProfile();
		user.setId("foo");
		user.setFirstName("FooUser");
		user.setLastName("Foo");
		
		req=repository.addUser(user);
		assertNotNull(req);

		user=repository.getUser(req);
		assertNotNull(user);
		
		LOG.info("UserProfile : {}",getJSON(user));

		req=new UserProfileRequest();
		req.setId("ppillai");
		repository.removeUser(req);
		
		repository.removeAllUsers();
		
	}
}
