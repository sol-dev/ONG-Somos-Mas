package com.team32.ong.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.team32.ong.dto.AddCommentBody;
import com.team32.ong.dto.CommentBodyDTO;
import com.team32.ong.dto.CommentDto;
import com.team32.ong.exception.custom.BadRequestException;
import com.team32.ong.service.CommentService;

import javassist.NotFoundException;



@RestController
@Validated
@RequestMapping("api/v1/news/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/addComment")
	public ResponseEntity<CommentDto> addComment(@RequestParam(name="newsId") Long newsId,
												 @RequestParam("userId") Long userId,
												 @RequestBody AddCommentBody commentBody) throws BadRequestException, NotFoundException {		
		
    	return commentService.createNewComment(newsId, userId, commentBody);
    	
	}

	@GetMapping("/comments")
	public ResponseEntity<List<CommentBodyDTO>> getAllOnlyBody(){
		return new ResponseEntity<>(commentService.getAllOnlyBody(),HttpStatus.OK);

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Long id,
									@RequestBody AddCommentBody commentBody,
									@RequestHeader("authorization") String token)
			throws Exception {

		return new ResponseEntity(commentService.update(id, commentBody, token), HttpStatus.OK);
	}
}
