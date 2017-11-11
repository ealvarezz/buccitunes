package com.buccitunes.controller;

import java.util.List;

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
	public @ResponseBody void removeUser(@RequestParam String following, @RequestParam String followed) {
		
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
		
		
		BucciResponse<SignupFormInfo> r = BucciResponseBuilder.successfulResponseMessage("My message returned", signupInfo);
		String theEmail = (String) r.getResponse().userInfo.getEmail() ;
		System.out.println("WE GOT EMAIL: \n=========================\n" +theEmail);
		
		try{
			User newUser = userService.signup(signupInfo);
			BucciResponse<User> success = BucciResponseBuilder.successfulResponseMessage("Successful Signup", newUser);
			return success;
		}
		catch(BucciException e) {
			BucciResponse<User> failed = BucciResponseBuilder.failedResponse(e.getErrMessage());
			return failed;
		}	
	}
	
	@RequestMapping(value="login", method = RequestMethod.POST)
	public @ResponseBody void login(@RequestBody LoginInfo loginInfo) {
		//TODO set session
		
		User account = userService.findOne(loginInfo.email);
		
		if(BucciPassword.checkPassword(loginInfo.password, account.getPassword())){
			
		}else{
			
		}
		
		
		
		
		
		
		
		return ;
	}
	
}
