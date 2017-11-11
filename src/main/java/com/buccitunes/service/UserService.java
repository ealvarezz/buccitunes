package com.buccitunes.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.buccitunes.dao.CreditCompanyRepository;
import com.buccitunes.dao.PremiumUserRepository;
import com.buccitunes.dao.UserRepository;
import com.buccitunes.miscellaneous.BucciException;
import com.buccitunes.miscellaneous.SignupFormInfo;
import com.buccitunes.model.CreditCompany;
import com.buccitunes.model.PremiumUser;
import com.buccitunes.model.User;

@Service
@Transactional
public class UserService  {
	
	private final UserRepository userRepository;
	private final PremiumUserRepository premiumUserRepository;
	private final CreditCompanyRepository creditCompanyRepository;
	
	public UserService(UserRepository userRepository, PremiumUserRepository premiumUserRepository, CreditCompanyRepository creditCompanyRepository) {
		
		this.userRepository = userRepository;
		this.premiumUserRepository = premiumUserRepository;
		this.creditCompanyRepository = creditCompanyRepository;
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
	
	public User findOne(String email){
		
		return userRepository.findOne(email);
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
	
	public User signup(SignupFormInfo signupInfo) throws BucciException {
		
		boolean signedForPremium = false;
		if(signupInfo.billingInfo != null) {
			signedForPremium = true;
		}
		
		User user = userRepository.findOne(signupInfo.userInfo.getEmail());
		
		if(user != null) {
			throw new BucciException("Email already being used");
		}
		
		user = signupInfo.userInfo;
		
		user.encryptAndSetPassword(signupInfo.userInfo.getPassword());
		
		if(signedForPremium) {
			String invalidBillingInfo = signupInfo.billingInfo.checkInvalidInfo(); 
			
			//If the billingInfo entered is invalid
			if(!invalidBillingInfo.equals("")) {
				throw new BucciException("Invalid Billing Infomation");
			}
			
			CreditCompany creditCompany = creditCompanyRepository.findByName(signupInfo.billingInfo.getCreditCardCompany().getName());
			
			if(creditCompany != null) {
				signupInfo.billingInfo.setCreditCardCompany(creditCompany);
			}
			else {
				throw new BucciException("Invalid Credit Card Company");
			}
			
		}
		
		if(signedForPremium) {
			PremiumUser pUser = new PremiumUser(user,signupInfo.billingInfo);
			PremiumUser newUser = premiumUserRepository.save(pUser);
			return newUser;
		}
		else {
			User newUser = userRepository.save(user);
			return newUser;
		}
	}
}
