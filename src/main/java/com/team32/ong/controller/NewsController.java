package com.team32.ong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.team32.ong.dto.NewsDto;
import com.team32.ong.service.NewsService;

@RestController
@RequestMapping("api/v1/news")
public class NewsController {
	
	@Autowired
	private NewsService newsService;
	
	@PostMapping("/new")
	public ResponseEntity<NewsDto> create(@RequestBody NewsDto newsDto,
			@RequestParam(name = "file", required = false) MultipartFile image) throws Exception{
		newsService.save(newsDto,image);
		return ResponseEntity.status(HttpStatus.CREATED).body(newsDto);	
	} 
}
