package com.team32.ong.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

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
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.team32.ong.exception.custom.EmptyInputException;

import javassist.NotFoundException;


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
    
    public List<Bucket> listBuckets() throws IOException {
    	List<Bucket> buckets = null;
    	try {
    		BasicAWSCredentials creds = new BasicAWSCredentials(this.accessKey, this.secretKey);
    	    AmazonS3 s3client = AmazonS3ClientBuilder.standard()
                    .withRegion("us-east-1")
                    .withCredentials(new AWSStaticCredentialsProvider(creds))
                    .build();
    	    buckets = s3client.listBuckets();
    	    if(!s3client.doesBucketExistV2(bucketName)){
    	    	throw new IOException("Problemas con Amazon. Nombre del bucket equivocado");
    	    } 
    	} catch (AmazonServiceException e) {
    		throw new IOException("Problemas con Amazon. AmazonServiceException. accessKey o secretKey son incorrectas");
        } catch (SdkClientException e) {
        	throw new IOException("Problemas con Amazon. SdkClientException");
        }
    	return buckets;
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
	
	private void uploadFileTos3bucket(String fileName, File file) throws IOException {
		List<Bucket> buckets = listBuckets();
		if( buckets != null) {
			BasicAWSCredentials creds = new BasicAWSCredentials(this.accessKey, this.secretKey);
		    AmazonS3 s3client = AmazonS3ClientBuilder.standard()
	                .withRegion("us-east-1")
	                .withCredentials(new AWSStaticCredentialsProvider(creds))
	                .build();
		    s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
	            .withCannedAcl(CannedAccessControlList.PublicRead));
	    } 
	}
	
	public ResponseEntity<String> uplodFileToS3Bucket(MultipartFile multipartFile) throws EmptyInputException, IOException {
		if(multipartFile != null && !multipartFile.isEmpty()) {
			String fileUrl = "";
	        File file = convertMultiPartToFile(multipartFile);
	        String fileName = generateFileName(multipartFile);
	        fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
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
		List<Bucket> buckets = listBuckets();
		if( buckets == null) {
			throw new IOException("Problemas con Amazon");
		} else {
		    String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
		    BasicAWSCredentials creds = new BasicAWSCredentials(this.accessKey, this.secretKey);
		    AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
	                .withRegion("us-east-1")
	                .withCredentials(new AWSStaticCredentialsProvider(creds))
	                .build();
		    if(s3Client.doesObjectExist(bucketName, fileName)) {
		    	s3Client.deleteObject(bucketName, fileName);
		    } else {
		    	throw new NotFoundException("El archivo " + fileName + " no existe");
		    }
		    return new ResponseEntity<>("El archivo " + bucketName + "/" + fileName + " se borró correctamente",HttpStatus.OK);
		}
	}
	
	public Boolean imageExists(String imageUrl) throws Throwable {
		if(imageUrl.isEmpty()|imageUrl.isBlank()) {
			throw new EmptyInputException("Tiene que poner la dirección URL de la imagen");
		}
		List<Bucket> buckets = listBuckets();
		if( buckets != null) {
		    String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
		    BasicAWSCredentials creds = new BasicAWSCredentials(this.accessKey, this.secretKey);
		    AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
	                .withRegion("us-east-1")
	                .withCredentials(new AWSStaticCredentialsProvider(creds))
	                .build();
		    if(s3Client.doesObjectExist(bucketName, fileName)) {
		    	return true;
		    } 
		}
		return false;
	}
		
}
    


	

