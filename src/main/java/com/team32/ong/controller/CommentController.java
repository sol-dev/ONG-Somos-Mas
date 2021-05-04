package com.team32.ong.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.team32.ong.dto.CommentDto;
import com.team32.ong.exception.custom.InvalidDataException;
import com.team32.ong.model.Comment;
import com.team32.ong.service.CommentService;

@RestController
@RequestMapping("api/v1/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping(path="/addComment", consumes={MediaType.APPLICATION_JSON_VALUE}, produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<CommentDto> addComment(@Valid @RequestBody CommentDto commentDto, BindingResult result){
	
        if(result.hasErrors()){
            throw new InvalidDataException(result);
        }
        
        Comment comment = commentService.dtoToModel(commentDto);
        commentService.save(comment);
        
        HttpHeaders httpHeaders = new HttpHeaders();
    	httpHeaders.add("Method", "addComment");
        
    	return ResponseEntity
    			.status(HttpStatus.OK)
    			.headers(httpHeaders)
    			.body(commentDto);
    	
	}
}

