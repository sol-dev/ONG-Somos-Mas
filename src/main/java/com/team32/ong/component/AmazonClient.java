package com.team32.ong.component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.team32.ong.exception.custom.EmptyInputException;
import com.team32.ong.exception.custom.Forbidden;
import javassist.NotFoundException;


@Component
public class AmazonClient {

    private final String ENDPOINT_URL = "https://s3.us-east-1.amazonaws.com";
    private final String BUCKET_NAME = "alkemy-somos-mas";
    private final String REGION = "us-east-1";
    
    @Value("${amazonProperties.accessKey}")
    private String accessKey;
    @Value("${amazonProperties.secretKey}")
    private String secretKey;

    public AmazonS3 makeS3Client() throws Throwable {
		BasicAWSCredentials creds = new BasicAWSCredentials(this.accessKey, this.secretKey);
	    AmazonS3 s3client = AmazonS3ClientBuilder.standard()
                .withRegion(REGION)
                .withCredentials(new AWSStaticCredentialsProvider(creds))
                .build();
	    if(!s3client.doesBucketExistV2(BUCKET_NAME)){
	    	throw new NotFoundException("No existe un bucket con ese nombre");
	    } 
	    return s3client;
    }
	
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
	
	private void uploadFileTos3bucket(String fileName, File file) throws Throwable {
		AmazonS3 s3client = makeS3Client();
	    s3client.putObject(new PutObjectRequest(BUCKET_NAME, fileName, file)
            .withCannedAcl(CannedAccessControlList.PublicRead));
	    
	}
	
	public ResponseEntity<String> uplodFileToS3Bucket(MultipartFile multipartFile) throws Throwable {
		if(multipartFile != null && !multipartFile.isEmpty()) {
			String fileUrl = "";
	        File file = convertMultiPartToFile(multipartFile);
	        String fileName = generateFileName(multipartFile);
	        fileUrl = ENDPOINT_URL + "/" + BUCKET_NAME + "/" + fileName;
	        uploadFileTos3bucket(fileName, file);
	        file.delete();
		    return new ResponseEntity<>(fileUrl,HttpStatus.OK);
		} else {
			throw new EmptyInputException("Tiene que seleccionar un archivo");
		}    
	}
	
	public ResponseEntity<String> deleteFileFromS3Bucket(String fileUrl) throws Throwable{
		if(fileUrl.isEmpty()|fileUrl.isBlank()) {
			throw new EmptyInputException("Tiene que poner la dirección URL del archivo que quiere borrar");
		}
	    String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
	    AmazonS3 s3client = makeS3Client();
	    if(s3client.doesObjectExist(BUCKET_NAME, fileName)) {
	    	s3client.deleteObject(BUCKET_NAME, fileName);
	    } else {
	    	throw new NotFoundException("El archivo " + fileName + " no existe");
	    }
		return new ResponseEntity<>("El archivo " + BUCKET_NAME + "/" + fileName + " se borró correctamente",HttpStatus.OK);
	}
	
	public Boolean imageExists(String imageUrl) throws Throwable {
		if(imageUrl.isEmpty()|imageUrl.isBlank()) {
			throw new EmptyInputException("Tiene que poner la dirección URL de la imagen");
		}
	    String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
	    AmazonS3 s3client = makeS3Client();
	    if(s3client.doesObjectExist(BUCKET_NAME, fileName)) {
	    	return true;
	    } 
		return false;
	}
		
}
    


	

