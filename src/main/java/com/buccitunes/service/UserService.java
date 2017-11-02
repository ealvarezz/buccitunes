package com.buccitunes.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.buccitunes.dao.UserRepository;
import com.buccitunes.model.User;

@Service
@Transactional
public class UserService {
	
	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		
		this.userRepository = userRepository;
	}
	
	public List<User> findAll(){
		
		List<User> result = new ArrayList<>();
		for(User user: userRepository.findAll()) result.add(user);
			

		return result;
	}
	
	public void save(User user) {
		
		
		userRepository.save(user);
	}
	
	public void remove(String email) {
		
		
		userRepository.delete(email);
	}
	
	public void follow(String follower, String followed) {
		
		User followingUser = userRepository.findOne(follower);
		User followedUser = userRepository.findOne(followed);
		
		followingUser.getFollowing().add(followedUser);
		
		
		
	}
	
	public List<User> getFollowing(String email){
		
		User user = userRepository.findOne(email);
		
		return user.getFollowing();
	}
	
	public List<User> getFollowers(String email){
		
		User user = userRepository.findOne(email);
		
		return user.getFollowers();
	}
	
	public List<User> findByName(String name) {
		
		return userRepository.findByName(name);
	}
	
	public void updateName(String email, String name) {
		
		User user = userRepository.findOne(email);
		
		user.setName(name);
		//userRepository.save(user);
	}

}
