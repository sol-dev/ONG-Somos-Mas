package com.team32.ong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.team32.ong.dto.AddCommentDto;
import com.team32.ong.dto.CommentDto;
import com.team32.ong.dto.NewsDto;
import com.team32.ong.dto.UserResponse;
import com.team32.ong.service.CommentService;
import com.team32.ong.service.NewsService;
import com.team32.ong.service.UserService;

@RestController
@RequestMapping("api/v1/comments")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private NewsService newsService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping()
	public ResponseEntity<CommentDto> addComment(@RequestBody AddCommentDto newComment){
		
		CommentDto commentDto = new CommentDto();
		NewsDto newsDto = new NewsDto();
		UserResponse userResponse = new UserResponse();
		
		if(newComment.getNewsId() > 0) {
			newsDto = newsService.getOne(newComment.getNewsId());
		}
		if(newComment.getUserId() > 0) {
			userResponse = userService.getOne(newComment.getUserId());
		}
		
		commentDto.setBody(newComment.getBody());
		commentDto.setNews(newsService.dtoToModel(newsDto));
		commentDto.setUser(userService.dtoToEntity(userResponse));
		
		commentService.save(commentDto);
		
    	return new ResponseEntity<>(commentDto,HttpStatus.ACCEPTED);
    	
	}
}
