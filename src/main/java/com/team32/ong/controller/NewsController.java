package com.team32.ong.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

	@GetMapping("/{id}")
	public ResponseEntity<NewsDto> getNew(@PathVariable("id") Long id) throws NotFoundException {

		NewsDto newsDto = newsService.findById(id);

		return new ResponseEntity<NewsDto>(newsDto, HttpStatus.OK);
	}
}
