package com.team32.ong.controller;

import com.team32.ong.dto.SlideDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import com.team32.ong.service.SlideService;

@RestController
@RequestMapping("api/v1/slides")
public class SlidesController {

	@Autowired
	private SlideService slideService;

	@GetMapping(value = "/list")
	public ResponseEntity<Map<Integer, String>> getImageAndOrderList() {
		return new ResponseEntity<>(slideService.imageAndOrder(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<SlideDto> save(SlideDto slideDto){
		return new ResponseEntity<>(slideService.save(slideDto), HttpStatus.CREATED);
	}

}
