package com.buccitunes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.buccitunes.service.ArtistService;
import com.buccitunes.service.UserService;


	
@Controller	
public class ArtistController {
	@Autowired
	private ArtistService artistService;
	
	
}
