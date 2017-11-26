package com.buccitunes.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.buccitunes.dao.BillingInfoRepository;
import com.buccitunes.dao.CreditCompanyRepository;
import com.buccitunes.dao.PremiumUserRepository;
import com.buccitunes.dao.UserRepository;
import com.buccitunes.jsonmodel.SignupFormInfo;
import com.buccitunes.miscellaneous.BucciException;
import com.buccitunes.model.BillingInfo;
import com.buccitunes.model.CreditCompany;
import com.buccitunes.model.PremiumUser;
import com.buccitunes.model.User;

@Service
@Transactional
public class UserService  {
	
	private final UserRepository userRepository;
	private final PremiumUserRepository premiumUserRepository;
	private final CreditCompanyRepository creditCompanyRepository;
	private final BillingInfoRepository billingInfoRepository;
	
	public UserService(UserRepository userRepository, PremiumUserRepository premiumUserRepository, 
			CreditCompanyRepository creditCompanyRepository, BillingInfoRepository billingInfoRepository) {
		
		this.userRepository = userRepository;
		this.premiumUserRepository = premiumUserRepository;
		this.creditCompanyRepository = creditCompanyRepository;
		this.billingInfoRepository = billingInfoRepository;
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
	
	public User follow(String follower, String followed) throws BucciException {
		
		User followingUser = userRepository.findOne(follower);
		User followedUser = userRepository.findOne(followed);
		
		if(followingUser == null || followedUser == null) {
			throw new BucciException("User not found");
		}
		
		followingUser.getFollowing().add(followedUser);
		return followedUser;
	}
	
	public User findOne(String email){
		
		return userRepository.findOne(email);
	}
	
	
	public List<User> getFollowing(String email) throws BucciException{
		
		User user = userRepository.findOne(email);
		if(user == null) {
			throw new BucciException("User not found"); 
		}
		
		return user.getFollowing();
	}
	
	public List<User> getFollowers(String email) throws BucciException{
		
		User user = userRepository.findOne(email);
		if(user == null) {
			throw new BucciException("User not found"); 
		}
		
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
				throw new BucciException(invalidBillingInfo);
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
	
	public PremiumUser upgradeToPremium(User user, BillingInfo billingInfo) throws BucciException {
		PremiumUser existingPremium = premiumUserRepository.findOne(user.getEmail());
		
		if(existingPremium != null) {
			throw new BucciException("You are already a premium user");
		}
		
		user = userRepository.findOne(user.getEmail());
		if(user == null) {
			throw new BucciException("Original user not found");
		}
		
		String invalidBillingInfo = billingInfo.checkInvalidInfo();
		if(!invalidBillingInfo.equals("") ) {
			throw new BucciException(invalidBillingInfo);
		}
		
		CreditCompany cardCompany = creditCompanyRepository.findOne(billingInfo.getCreditCardCompany().getId());
		if(cardCompany == null) {
			throw new BucciException("Invalid Billing Infomation");
		}
		
		billingInfo.setCreditCardCompany(cardCompany);
		billingInfo = billingInfoRepository.save(billingInfo);
		premiumUserRepository.upgradeToPremium(user.getEmail(), billingInfo.getId());		
		PremiumUser pUser = premiumUserRepository.findOne(user.getEmail());

		return pUser;
	}
}
