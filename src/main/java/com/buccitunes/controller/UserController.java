package com.buccitunes.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.buccitunes.miscellaneous.*;
import com.buccitunes.model.BillingInfo;
import com.buccitunes.model.PremiumUser;
import com.buccitunes.model.User;
import com.buccitunes.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String hello() {
		return "index";
	}
	
	@RequestMapping(value="getallusers", method = RequestMethod.GET)
	public @ResponseBody List<User> getAllUsers() {
		return userService.findAll();
	}
	
	@RequestMapping(value="adduser", method = RequestMethod.POST)
	public @ResponseBody void addUser(@RequestBody User user) {
		userService.save(user);
	}
	
	@RequestMapping(value="deleteuser", method = RequestMethod.GET)
	public @ResponseBody void removeUser(@RequestParam String email) {
		userService.remove(email);
	}
	
	@RequestMapping(value="follow", method = RequestMethod.GET)
	public @ResponseBody void followUser(@RequestParam String following, @RequestParam String followed) {
		userService.follow(following, followed);
	}
	
	@RequestMapping(value="getfollowers", method = RequestMethod.GET)
	public @ResponseBody List<User> getAllFollowers(@RequestParam String email) {
		return userService.getFollowers(email);
	}
	
	@RequestMapping(value="getfollowing", method = RequestMethod.GET)
	public @ResponseBody List<User> getAllFollowings(@RequestParam String email) {
		return userService.getFollowing(email);
	}
	
	@RequestMapping(value="findbyname", method = RequestMethod.GET)
	public @ResponseBody List<User> findUserByName(@RequestParam String name) {
		return userService.findByName(name);
	}
	
	@RequestMapping(value="updatename", method = RequestMethod.GET)
	public @ResponseBody void updateUserName(@RequestParam String email, @RequestParam String name) {
		userService.updateName(email, name);
	}
	
	@RequestMapping(value="signup", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<User> updateUserName(@RequestBody SignupFormInfo signupInfo) {
		User newUser;
		
		try{
			newUser = userService.signup(signupInfo);
			BucciResponse<User> success = BucciResponseBuilder.successfulResponseMessage("Successful Signup", newUser);
			return success;
		} catch(BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}	
	}
	
	@RequestMapping(value="login", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<User> login(@RequestBody LoginInfo loginInfo, HttpSession session) {
		User account = userService.findOne(loginInfo.email);
		
		if(account != null) {
			return BucciResponseBuilder.failedMessage("Already Logged In");
		}
		if(BucciPassword.checkPassword(loginInfo.password, account.getPassword())) {
			session.setAttribute("user", account);
			return BucciResponseBuilder.successfulResponseMessage("Successful Login", account);	
		} else {
			return BucciResponseBuilder.failedMessage("Invalid Login Information");
		}
	}
	
	@RequestMapping(value="logout", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<String> logout(@RequestBody User user, HttpSession session) {
		User sessionUser = (User) session.getAttribute("user");
		
		if(sessionUser == null) {
			return BucciResponseBuilder.failedMessage("Not Logged In");
		} 
		if(!sessionUser.getEmail().equals(user.getEmail())) {
			return BucciResponseBuilder.failedMessage("Invalid Email");
		}
		session.removeAttribute("user");
		return BucciResponseBuilder.successMessage("LoggedOut");
	}
	
	@RequestMapping(value="sessionInfo", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<User> sessionTest(HttpSession session) {	
		User sessionUser = (User) session.getAttribute("user");
		return BucciResponseBuilder.successfulResponse(sessionUser);
	}
	
	@RequestMapping(value="premiumUpgrade", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<PremiumUser> premiumUpgrade(@RequestBody BillingInfo billingInfo, HttpSession session) {
		User sessionUser = (User) session.getAttribute("user");
		
		if(sessionUser == null) {
			return BucciResponseBuilder.failedMessage("Not Logged In");
		}
		try {
			PremiumUser user = userService.upgradeToPremium(sessionUser, billingInfo);
			session.setAttribute("user", user);
			sessionUser = user;
		} catch (BucciException e) {
			return BucciResponseBuilder.failedMessage(e.getErrMessage());
		}
		return BucciResponseBuilder.successfulResponseMessage("Congratulations, you have upgraded to premium", (PremiumUser)sessionUser);
	}
	/*
	@RequestMapping(value="followingAlbums", method = RequestMethod.POST)
	public @ResponseBody BucciResponse<User> followingAlbums(@RequestBody BillingInfo billingInfo, HttpSession session) {
		
	}
	*/
}
