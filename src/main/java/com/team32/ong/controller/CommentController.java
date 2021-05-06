package com.team32.ong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.team32.ong.dto.AddCommentBody;
import com.team32.ong.dto.CommentDto;
import com.team32.ong.service.CommentService;


@RestController
@RequestMapping("api/v1/news")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/addComment")
	public ResponseEntity<CommentDto> addComment(@RequestParam("newsId") Long newsId,
												 @RequestParam("userId") Long userId,
												 @RequestBody AddCommentBody commentBody) {

		CommentDto newCommentDto = commentService.createNewComment(newsId, userId, commentBody.getBody());
		commentService.save(newCommentDto);
		
    	return new ResponseEntity<>(newCommentDto,HttpStatus.ACCEPTED);
    	
	}
}
