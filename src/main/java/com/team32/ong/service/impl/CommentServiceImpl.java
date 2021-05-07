package com.team32.ong.service.impl;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.team32.ong.dto.AddCommentBody;
import com.team32.ong.dto.CommentDto;
import com.team32.ong.dto.NewsDto;
import com.team32.ong.dto.UserResponse;
import com.team32.ong.exception.custom.BadRequestException;
import com.team32.ong.exception.custom.EmptyInputException;
import com.team32.ong.exception.custom.InvalidDataException;
import com.team32.ong.model.Comment;
import com.team32.ong.model.User;
import com.team32.ong.repository.CommentRepository;
import com.team32.ong.service.CommentService;
import com.team32.ong.service.NewsService;
import com.team32.ong.service.UserService;

import javassist.NotFoundException;

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
	public CommentDto findById(Long id) {
		Comment comment = commentRepository.findById(id).orElse(null);
    	return modelToDto(comment);
	}

	@Override
	public ResponseEntity<CommentDto> createNewComment(Optional<Long> newsId, Optional<Long> userId, AddCommentBody commentBody){
		CommentDto commentDto = new CommentDto();
		NewsDto newsDto = new NewsDto();
		UserResponse userResponse = new UserResponse();
		
		if(!newsId.isPresent()) {
			throw new EmptyInputException("El parámetro newsId es obligatorio");
		} else {
			Long newsLongId = newsId.get();
			newsDto = newsService.findById(newsLongId);
			if(!newsDto.equals(null)) {
				commentDto.setNews(newsService.dtoToModel(newsDto));
			}
		}
			
		if(!userId.isPresent()) {
			throw new EmptyInputException("El parámetro userId es obligatorio");
		} else {
			Long userLongId = userId.get();
			userResponse = userService.findById(userLongId);
			if(!userResponse.equals(null)) {
				commentDto.setUser(userService.dtoToEntity(userResponse));
			}
		}
		
		if(commentBody == null) {
			throw new EmptyInputException("Tiene que existir un comentario");
		} else if(commentBody.getBody().isBlank() | commentBody.getBody().isEmpty()) {
			throw new EmptyInputException("El cuerpo del comentario no puede estar vacío");
		} else {
			commentDto.setBody(commentBody.getBody());	
		}
				
		commentDto.setId(0L);
		save(commentDto);
		
		return new ResponseEntity<>(commentDto,HttpStatus.OK);
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
