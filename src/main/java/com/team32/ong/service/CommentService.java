package com.team32.ong.service;

<<<<<<< HEAD
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.team32.ong.dto.AddCommentBody;
import com.team32.ong.dto.CommentDto;

@Service
public interface CommentService {
	
	CommentDto save(CommentDto commentDto);
	CommentDto getOne(Long id);
	CommentDto findById(Long id);
	ResponseEntity<CommentDto> createNewComment(Long newsId, Long userId, AddCommentBody commentBody);
	AddCommentBody update(Long id, AddCommentBody commentBody) throws Exception;
=======
import com.team32.ong.dto.AddCommentBody;
import com.team32.ong.dto.CommentDto;
import com.team32.ong.model.Comment;

import java.util.Optional;

public interface CommentService {

 AddCommentBody update(Long id, AddCommentBody commentDto) throws Exception;
 boolean existsById(Long id) throws Exception;
 Optional<Comment> findById(Long id) throws Exception;
>>>>>>> 69689d3f802e979a07c7560b01995bba9b6d064f
}
