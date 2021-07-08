package com.team32.ong.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team32.ong.constant.ConstantExceptionMessage;
import com.team32.ong.dto.AddCommentBody;
import com.team32.ong.dto.CommentBodyDTO;
import com.team32.ong.dto.CommentDto;
import com.team32.ong.dto.NewsDto;
import com.team32.ong.dto.UserDTOResponse;
import com.team32.ong.exception.custom.BadRequestException;
import com.team32.ong.exception.custom.EmptyInputException;
import com.team32.ong.exception.custom.ForbiddenException;
import com.team32.ong.model.Comment;
import com.team32.ong.model.User;
import com.team32.ong.repository.CommentRepository;
import com.team32.ong.repository.UserRepository;
import com.team32.ong.repository.NewsRepository;
import com.team32.ong.service.CommentService;
import com.team32.ong.service.NewsService;
import com.team32.ong.service.UserService;

import javassist.NotFoundException;

import com.team32.ong.security.JWTUtil;
import org.springframework.security.access.AccessDeniedException;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private NewsService newsService;
	@Autowired
	private NewsRepository newsRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JWTUtil jwtUtil;

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
			//commentDto.setUser(userResponse);
		}
	
		if(commentBody == null) {
			throw new EmptyInputException(ConstantExceptionMessage.MSG_COMMENT_EMPTY);
		} else if(commentBody.getBody().isBlank() | commentBody.getBody().isEmpty()) {
			throw new EmptyInputException(ConstantExceptionMessage.MSG_COMMENT_BODY_EMPTY);
		} else {
			commentDto.setBody(commentBody.getBody());	
		}
				
		commentDto.setId(0L);
		save(commentDto);
		
		return new ResponseEntity<>(commentDto,HttpStatus.OK);
	}
	
	@Override
	public void delete(Long id) throws NotFoundException{
		String userEmail = (String)SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByEmail(userEmail);
		Comment comment = commentRepository.findById(id).orElseThrow(() -> new NotFoundException(ConstantExceptionMessage.MSG_NOT_FOUND_COMMENT + id));
		String emailComment = comment.getUser().getEmail();
		if(user.getRole().getName().equalsIgnoreCase("ROLE_ADMIN")) {
			commentRepository.deleteById(id);
		}else if (emailComment.equals(userEmail)) {
				commentRepository.deleteById(id);
		}else {
			throw new ForbiddenException(ConstantExceptionMessage.MSG_COMMENT_BAD_REQUEST);
		}
	}	

	@Override
	public AddCommentBody update(Long id, AddCommentBody commentBody, String token) throws Exception {

		Comment oldComment = commentRepository.findById(id).orElse(null);
		if (oldComment == null){
			throw new NotFoundException(ConstantExceptionMessage.MSG_COMMENT_NOT_FOUND.concat(id.toString()));
		}


		User user = userRepository.findByEmail(jwtUtil.extractUsername(token.substring(7)));
		if (oldComment.getUser().getId() != user.getId() && ! user.getRole().getName().equals("ROLE_ADMIN")){
			throw new AccessDeniedException(ConstantExceptionMessage.MSG_ACCES_DENIED);
		}

		oldComment.setBody(commentBody.getBody());

		commentRepository.save(oldComment);

		return commentBody;
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

	@Override
	public List<CommentBodyDTO> getAllOnlyBody() {
		 List<CommentBodyDTO> listFound = commentRepository.findAll()
											 				.stream()
											 				.map(this::modelToBodyDto)
											 				.collect(Collectors.toList());
		 return listFound;
	}

	@Override
	public List<CommentBodyDTO> getCommentsByNewsId(Long id) throws NotFoundException {
		newsRepository.findById(id)
						.orElseThrow(() -> new NotFoundException(ConstantExceptionMessage.MSG_NEWS_NOT_FOUND + id));
		List<Comment> listFound = commentRepository.findCommentsByNewsId(id);
		return listFound.stream()
						.map(this::modelToBodyDto)
						.collect(Collectors.toList());
	}
}
