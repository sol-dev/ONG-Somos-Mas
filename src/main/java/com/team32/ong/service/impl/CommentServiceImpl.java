package com.team32.ong.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team32.ong.dto.AddCommentBody;
import com.team32.ong.dto.CommentBodyDTO;
import com.team32.ong.dto.CommentDTOByIdNews;
import com.team32.ong.dto.CommentDto;
import com.team32.ong.dto.NewsDto;
import com.team32.ong.dto.UserDTOResponse;
import com.team32.ong.exception.custom.BadRequestException;
import com.team32.ong.exception.custom.EmptyInputException;
import com.team32.ong.model.Comment;
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
	public CommentDto save(CommentDto commentDto) throws BadRequestException{
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


	public ResponseEntity<CommentDto> createNewComment(Long newsId, Long userId, AddCommentBody commentBody)throws BadRequestException, NotFoundException{
		
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
	
	public CommentBodyDTO modelToBodyDto(Comment comment) {
		ModelMapper mapper = new ModelMapper();
		CommentBodyDTO commentDto = mapper.map(comment, CommentBodyDTO.class);
		return commentDto;
	}
	
	public CommentDTOByIdNews modelToDtoByNewsId(Comment comment) {
		ModelMapper mapper = new ModelMapper();
		CommentDTOByIdNews commentDto = mapper.map(comment, CommentDTOByIdNews.class);
		return commentDto;
	}

	@Override
	public List<CommentBodyDTO> getAllOnlyBody() {
		 List<CommentBodyDTO> listFound = commentRepository.findAll()
											 				.stream()
											 				.map(this::modelToBodyDto)
											 				.collect(Collectors.toList());
		 return listFound;
	}
	
	@Override
	public List<CommentDTOByIdNews> getCommentsByNewsId(Long id) throws NotFoundException {
		newsService.findById(id);
		List<Comment> listFound = commentRepository.findCommentsByNewsId(id);
		return listFound.stream()
						.map(this::modelToDtoByNewsId)
						.collect(Collectors.toList());
	}
}
