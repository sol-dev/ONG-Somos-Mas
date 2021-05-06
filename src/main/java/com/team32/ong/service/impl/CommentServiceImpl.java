package com.team32.ong.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.team32.ong.dto.CommentDto;
import com.team32.ong.dto.NewsDto;
import com.team32.ong.dto.UserResponse;
import com.team32.ong.model.Comment;
import com.team32.ong.repository.CommentRepository;
import com.team32.ong.service.CommentService;
import com.team32.ong.service.NewsService;
import com.team32.ong.service.UserService;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private NewsService newsService;
	@Autowired
	private UserService userService;

	@Override
	public CommentDto save(CommentDto commentDto) {
		Comment comment = this.dtoToModel(commentDto);
		comment.setDeleted(false);
		Comment newComment = commentRepository.save(comment);	
		return modelToDto(newComment);	
	}

	@Override
	public CommentDto getOne(Long id) {
		Comment comment = commentRepository.getOne(id);
		return modelToDto(comment);
	}	

	@Override
	public CommentDto createNewComment(Long newsId, Long userId, String body) {
		CommentDto commentDto = new CommentDto();
		NewsDto newsDto = new NewsDto();
		UserResponse userResponse = new UserResponse();
		if(newsId > 0) {
			newsDto = newsService.getOne(newsId);
		}
		if(userId > 0) {
			userResponse = userService.getOne(userId);
		}
		if(body == null | body.length() == 0) {
			
		}	
		commentDto.setBody(body);
		commentDto.setNews(newsService.dtoToModel(newsDto));
		commentDto.setUser(userService.dtoToEntity(userResponse));
		return commentDto;
	}
	
	
	public CommentDto modelToDto(Comment comment) {
		ModelMapper mapper = new ModelMapper();
        CommentDto commentDto = mapper.map(comment, CommentDto.class);
		return commentDto;
	}


	public Comment dtoToModel(CommentDto commentDto) {
		ModelMapper mapper = new ModelMapper();
		Comment comment = mapper.map(commentDto, Comment.class);
		return comment;
	}


}
