package com.team32.ong.controller;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.team32.ong.dto.AddCommentBody;
import com.team32.ong.dto.CommentDto;
import com.team32.ong.service.CommentService;

import java.util.HashMap;
import java.util.Map;


@RestController
@Validated
@RequestMapping("api/v1/news/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/addComment")
	public ResponseEntity<CommentDto> addComment(@RequestParam(name="newsId") Long newsId,
												 @RequestParam("userId") Long userId,
												 @RequestBody AddCommentBody commentBody, BindingResult result) {		
		
    	return commentService.createNewComment(newsId, userId, commentBody);
    	
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody AddCommentBody commentBody)
			throws Exception {

		Map<String, Object> response = new HashMap<>();
		AddCommentBody commentResponse = null;

		commentResponse = commentService.update(id, commentBody);

		response.put("comment", commentResponse);

		return new ResponseEntity(response, HttpStatus.OK);
	}
}
