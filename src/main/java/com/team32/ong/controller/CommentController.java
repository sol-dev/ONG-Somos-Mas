package com.team32.ong.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.team32.ong.dto.AddCommentDto;
import com.team32.ong.dto.CommentDto;
import com.team32.ong.dto.NewsDto;
import com.team32.ong.dto.UserDto;
import com.team32.ong.exception.custom.InvalidDataException;
import com.team32.ong.model.Comment;
import com.team32.ong.model.News;
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
		UserDto userDto = new UserDto();
		
		if(newComment.getNewsId() > 0) {
			newsDto = newsService.getOne(newComment.getNewsId());
		}
		if(newComment.getUserId() > 0) {
			userDto = userService.getOne(newComment.getUserId());
		}
		
		commentDto.setBody(newComment.getBody());
		commentDto.setNews(newsService.dtoToModel(newsDto));
		commentDto.setUser(userService.dtoToEntity(userDto));
		
		commentService.save(commentDto);
		
    	return new ResponseEntity<>(commentDto,HttpStatus.ACCEPTED);
    	
	}
}
