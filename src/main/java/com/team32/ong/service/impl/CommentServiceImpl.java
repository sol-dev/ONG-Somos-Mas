package com.team32.ong.service.impl;

import com.team32.ong.constant.ConstantMessage;


import com.team32.ong.dto.UserDTOResponse;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.team32.ong.dto.AddCommentBody;
import com.team32.ong.dto.CommentDto;
import com.team32.ong.dto.NewsDto;
import com.team32.ong.exception.custom.EmptyInputException;
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
	public CommentDto findById(Long id) {
		Comment comment = commentRepository.findById(id).orElse(null);
    	return modelToDto(comment);
	}

	public ResponseEntity<CommentDto> createNewComment(Long newsId, Long userId, AddCommentBody commentBody){
		
		CommentDto commentDto = new CommentDto();
		
		NewsDto newsDto = newsService.findById(newsId);
		if(!newsDto.equals(null)) {
			commentDto.setNews(newsService.dtoToModel(newsDto));
		}

		UserDTOResponse userResponse = userService.findById(userId);
		if(!userResponse.equals(null)) {
			commentDto.setUser(userResponse);
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

	@Override
	public AddCommentBody update(Long id, AddCommentBody commentBody) throws Exception {

		Comment oldComment = commentRepository.findById(id).get();
		if (oldComment == null){
			throw new NotFoundException(ConstantMessage.MSG_COMMENT_NOT_FOUND.concat(id.toString()))
		}

		if (commentBody.getBody() == null || commentBody.getBody() == ""){
			throw new EmptyInputException(ConstantMessage.MSG_EMPTY_COMMENT_BODY);
		}

		//todo: validar usuario

		Comment newComment = new Comment();
		newComment.setId(oldComment.getId());
		newComment.setBody(commentBody.getBody());
		newComment.setDeleted(false);
		newComment.setUser(oldComment.getUser());
		newComment.setNews(oldComment.getNews());

		commentRepository.save(newComment);

		return commentBody;
	}

	@Override
	public boolean existsById(Long id) throws Exception {
		return false;
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
