package com.team32.ong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.TreeMap;

import com.team32.ong.dto.SlideDto;
import com.team32.ong.service.SlideService;

import javassist.NotFoundException;

@RestController
@RequestMapping("api/v1/slides")
public class SlidesController {

	@Autowired
	private SlideService slideService;

	@GetMapping("/slides/{id}")
	public ResponseEntity<SlideDto> getSlide(@PathVariable Long id) throws NotFoundException {
		return new ResponseEntity<>(slideService.findById(id), HttpStatus.OK);
	}

	@GetMapping(value = "/list")
	public ResponseEntity<TreeMap<String, TreeMap<Integer, String>>> getImageAndOrderList() {
		return new ResponseEntity<>(slideService.imageAndOrderByOrganization(), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteSlide(@PathVariable("id") Long id) throws Throwable {

		slideService.deleteSlide(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
