package com.team32.ong.controller;

import com.team32.ong.dto.SlideDto;
import com.team32.ong.dto.SlideDtoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.TreeMap;
import com.team32.ong.service.SlideService;
import javassist.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

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

	@PostMapping("/{idOrganization}")
	public ResponseEntity<SlideDto> save(@RequestBody SlideDtoRequest slideDto,
										 @PathVariable("idOrganization") Long idOrganization) throws Throwable{
		return new ResponseEntity<>(slideService.save(slideDto, idOrganization), HttpStatus.CREATED);
	}

	@PutMapping("/image/{idSlide}")
	public ResponseEntity<SlideDto> updateImage(@RequestPart(required=true) MultipartFile file, @PathVariable("idSlide") Long id) throws Throwable {

		return new ResponseEntity<>(slideService.updateImage(file, id), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<SlideDto> update(@PathVariable("id") Long id,
									       @RequestBody SlideDtoRequest slideDtoRequest)
			                               throws NotFoundException {
		return new ResponseEntity<SlideDto>(slideService.update(id, slideDtoRequest),
				HttpStatus.OK);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteSlide(@PathVariable("id") Long id) throws Throwable {

		slideService.deleteSlide(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}

