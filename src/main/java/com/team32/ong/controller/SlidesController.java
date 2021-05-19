package com.team32.ong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.net.MalformedURLException;
import com.team32.ong.service.SlideService;

@RestController
@RequestMapping("api/v1/slides")
public class SlidesController {

	@Autowired
	private SlideService slideService;

	@GetMapping(value = "/image", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
	@ResponseBody
	public ResponseEntity<Resource> getImage(String imageUrl) throws MalformedURLException {
		HttpHeaders headers = new HttpHeaders();
		Resource resource = new UrlResource(imageUrl);
		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}

}
