package com.team32.ong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;
import com.team32.ong.dto.ImageAndOrderDto;
import com.team32.ong.repository.SlideRepository;
import com.team32.ong.service.SlideService;

@RestController
@RequestMapping("api/v1/slides")
public class SlidesController {

	@Autowired
	private SlideService slideService;

	@Autowired
	private SlideRepository slideRepository;

	@GetMapping(value = "/list")
	public ResponseEntity<Map<String, List<ImageAndOrderDto>>> getImageAndOrderList() {
		return new ResponseEntity<>(slideService.imageAndOrder(), HttpStatus.OK);
	}

}
