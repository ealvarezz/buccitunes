package com.buccitunes.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.buccitunes.miscellaneous.BucciResponse;
import com.buccitunes.model.*;
import com.buccitunes.service.MusicCollectionService;
import com.buccitunes.service.UserService;

@Controller
public class MusicCollectionController {
	
	@Autowired
	private MusicCollectionService musicService;
	
	
	@RequestMapping(value="getNewReleases", method = RequestMethod.GET)
	public @ResponseBody BucciResponse<List<Album>> newReleasedAlbums() {
		
		return null;
		
	}
}
