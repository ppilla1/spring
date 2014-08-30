package io.ppillai.spring.redis.repository;

import io.ppillai.spring.redis.repository.model.UserProfile;
import io.ppillai.spring.redis.repository.model.UserProfileRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class RedisSubRegisterUserService implements MessageListener {
	private static final Logger LOG = LoggerFactory.getLogger(RedisSubRegisterUserService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void onMessage(Message message, byte[] pattern) {
		UserProfile user = null;
		
		if (null!=message && ArrayUtils.isNotEmpty(message.getBody())){
			ByteArrayInputStream bis = new ByteArrayInputStream(message.getBody());
			try {
				ObjectInputStream ois = new ObjectInputStream(bis);
				user = (UserProfile)ois.readObject();
				UserProfileRequest req = userRepository.addUser(user);
				LOG.info("UserProfile for {} added in repository", req.getId());
			} catch (IOException e) {
				LOG.error("Exception occured :", e);
			} catch (ClassNotFoundException e) {
				LOG.error("Exception occured :", e);
			}
		}
	}

}
