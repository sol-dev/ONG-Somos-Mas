package com.team32.ong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.team32.ong.service.AmazonClient;

@RestController
@RequestMapping("/api/v1/storage/")
public class BucketController {
	
	@Autowired
	private AmazonClient amazonClient;

    @PostMapping(value="/uploadFile")
    public ResponseEntity<String>  uploadFile(@RequestPart(required=true) MultipartFile file) throws Throwable{
        return amazonClient.uplodFileToS3Bucket(file);
    }
    
    @DeleteMapping("/deleteFile")
    public ResponseEntity<String>  deleteFilePrueba(@RequestParam String fileUrl) throws Throwable{
        return amazonClient.deleteFileFromS3Bucket(fileUrl);
    }

    @GetMapping(value="/fileExists")
    public Boolean fileExists(@RequestParam String imageUrl) throws Throwable{
        return amazonClient.imageExists(imageUrl);
    }
}
