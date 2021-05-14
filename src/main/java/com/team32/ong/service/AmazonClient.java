package com.team32.ong.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.team32.ong.exception.custom.EmptyInputException;


@Service
public class AmazonClient {
	 
    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;
    @Value("${amazonProperties.bucketName}")
    private String bucketName;
    @Value("${amazonProperties.accessKey}")
    private String accessKey;
    @Value("${amazonProperties.secretKey}")
    private String secretKey;
    
    
    // pruebas
    //private String endpointUrl = "https://us-east-1.amazonaws.com";
	
	private File convertMultiPartToFile(MultipartFile file) throws IOException {
	    File convFile = new File(file.getOriginalFilename());
	    FileOutputStream fos = new FileOutputStream(convFile);
	    fos.write(file.getBytes());
	    fos.close();
	    return convFile;
	}
	
	private String generateFileName(MultipartFile multiPart) {
	    return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
	}
	
	private void uploadFileTos3bucket(String fileName, File file) {
		BasicAWSCredentials creds = new BasicAWSCredentials(this.accessKey, this.secretKey);
	    AmazonS3 s3client = AmazonS3ClientBuilder.standard()
                .withRegion("us-east-1")
                .withCredentials(new AWSStaticCredentialsProvider(creds))
                .build();
	    s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
	            .withCannedAcl(CannedAccessControlList.PublicRead));
	}
	
	public ResponseEntity<String> uplodFileToS3Bucket(MultipartFile multipartFile) throws EmptyInputException, IOException {
		if(multipartFile != null && !multipartFile.isEmpty()) {
			String fileUrl = "";
			try {
		        File file = convertMultiPartToFile(multipartFile);
		        String fileName = generateFileName(multipartFile);
		        fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
		        uploadFileTos3bucket(fileName, file);
		        file.delete();
			} catch (IOException ioe) {
				throw new IOException("No se pudo cargar la imagen: " + multipartFile, ioe);
			}
		    return new ResponseEntity<>(fileUrl,HttpStatus.OK);
		} else {
			throw new EmptyInputException("Tiene que cargar un archivo");
		}    
	}
	
	public ResponseEntity<String> deleteFileFromS3Bucket(String fileUrl) {
		if(fileUrl.isEmpty()|fileUrl.isBlank()) {
			throw new EmptyInputException("Tiene que poner la dirección URL del archivo que quiere borrar");
		}
	    String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
	    BasicAWSCredentials creds = new BasicAWSCredentials(this.accessKey, this.secretKey);
	    AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion("us-east-1")
                .withCredentials(new AWSStaticCredentialsProvider(creds))
                .build();
	    s3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
	    return new ResponseEntity<>("El archivo " + bucketName + "/" + fileName + " se borró correctamente",HttpStatus.OK);
	}	
    
}

	

