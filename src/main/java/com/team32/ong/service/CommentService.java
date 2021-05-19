package com.team32.ong.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.team32.ong.dto.AddCommentBody;
import com.team32.ong.dto.CommentDto;
import com.team32.ong.exception.custom.BadRequestException;

import javassist.NotFoundException;

@Service
public interface CommentService {
	
	CommentDto save(CommentDto commentDto) throws BadRequestException;
	CommentDto getOne(Long id);
	CommentDto findById(Long id);
	void delete(Long id) throws NotFoundException;
	ResponseEntity<CommentDto> createNewComment(Long newsId, Long userId, AddCommentBody commentBody) throws BadRequestException, NotFoundException;

}
