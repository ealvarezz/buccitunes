package com.buccitunes.controller;


import javax.servlet.http.HttpSession;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;




@Controller
public class testCaching {
	static int counter = 0;
	
	//Cache the result of this method
	@Cacheable("testCaching")
	@RequestMapping(value="testCaching", method = RequestMethod.GET)
	public @ResponseBody Integer testCaching(){
		return counter;
	}
	
	//Cannot be the same function as the cacheable
	//Clear the cache every 300000 milliseconds (30 seconds). 
	@Scheduled(fixedRate=30000)
	@CacheEvict(allEntries=true, cacheNames={"testCaching"})
	public void clearTest(){}
	
	@RequestMapping(value="putCache", method = RequestMethod.GET)
	public @ResponseBody Integer putCache(){
		counter = counter+1;
		return counter;
	}
}
