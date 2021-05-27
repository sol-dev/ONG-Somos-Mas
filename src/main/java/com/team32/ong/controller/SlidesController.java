package com.team32.ong.controller;

import com.team32.ong.dto.SlideDto;
import com.team32.ong.dto.SlideDtoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import com.team32.ong.service.SlideService;

import javassist.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/slides")
public class SlidesController {

	@Autowired
	private SlideService slideService;
	
	@GetMapping("/slides/{id}")
	public ResponseEntity<SlideDto> getSlide(@PathVariable Long id) throws NotFoundException{
		return new ResponseEntity<>(slideService.findById(id),HttpStatus.OK);
	}

	@GetMapping(value = "/list")
	public ResponseEntity<Map<Integer, String>> getImageAndOrderList() {
		return new ResponseEntity<>(slideService.imageAndOrder(), HttpStatus.OK);
	}

	@PostMapping("/{idOrganization}")
	public ResponseEntity<SlideDto> save(@RequestBody SlideDtoRequest slideDto,
										 @RequestPart MultipartFile file,
										 @PathVariable("idOrganization") Long idOrganization) throws Throwable{
		return new ResponseEntity<>(slideService.save(slideDto, file, idOrganization), HttpStatus.CREATED);
	}

}
