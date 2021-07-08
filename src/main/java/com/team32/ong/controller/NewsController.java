package com.team32.ong.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.team32.ong.dto.NewsDto;
import com.team32.ong.exception.custom.BadRequestException;
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

	@PutMapping("/{id}")
	public ResponseEntity<NewsDto> update(@PathVariable("id") Long id, @RequestBody NewsDto newsDto) throws NotFoundException {
		return new ResponseEntity<>(newsService.update(id, newsDto), HttpStatus.CREATED);
	}

	@DeleteMapping("/{idNews}")
	public ResponseEntity<?> delete(@PathVariable("idNews") Long id) throws NotFoundException {
		if (newsService.deleteNew(id)){
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/{id}")
	public ResponseEntity<NewsDto> getNew(@PathVariable("id") Long id) throws NotFoundException {
		

		return new ResponseEntity<NewsDto>( newsService.findById(id), HttpStatus.OK);

	}

	@GetMapping("/")
	public ResponseEntity<String> getAll(@PageableDefault(size = 10) Pageable page) throws BadRequestException{
		return new ResponseEntity<>(newsService.getAll(page), HttpStatus.OK);
	}

}
