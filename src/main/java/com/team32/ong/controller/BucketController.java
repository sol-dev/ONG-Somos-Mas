package com.team32.ong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public String uploadFile(@RequestPart(value = "file", required=false) MultipartFile file) throws Exception{
        return this.amazonClient.uploadFile(file);
    }

    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestPart String fileUrl) {
        return this.amazonClient.deleteFileFromS3Bucket(fileUrl);
    }
    
    @PostMapping(value="/prueba")
    public String prueba(@RequestParam String fileName) throws Exception{
        return this.amazonClient.putObject(fileName) ;
    }

    
}
