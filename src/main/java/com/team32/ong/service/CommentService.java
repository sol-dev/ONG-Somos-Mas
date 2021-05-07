package com.team32.ong.service;

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
	ResponseEntity<CommentDto> createNewComment(Optional<Long> newsId, Optional<Long> userId, AddCommentBody commentBody);

}
