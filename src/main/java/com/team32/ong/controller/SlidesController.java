package com.team32.ong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team32.ong.service.SlideService;

@RestController
@RequestMapping("api/v1/slides")
public class SlidesController {
	
	@Autowired
	private SlideService slideService;

}
