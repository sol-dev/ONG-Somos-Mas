package com.team32.ong.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.team32.ong.dto.AddCommentBody;
import com.team32.ong.dto.CommentBodyDTO;
import com.team32.ong.dto.CommentDto;
import com.team32.ong.exception.custom.BadRequestException;

import javassist.NotFoundException;


@Service
public interface CommentService {

	CommentDto save(CommentDto commentDto) throws BadRequestException;
	CommentDto getOne(Long id);
	CommentDto findById(Long id);
	ResponseEntity<CommentDto> createNewComment(Long newsId, Long userId, AddCommentBody commentBody) throws BadRequestException, NotFoundException;
	List<CommentBodyDTO> getAllOnlyBody();
	List<CommentBodyDTO> getCommentsByNewsId(Long id) throws NotFoundException;
    AddCommentBody update(Long id, AddCommentBody commentDto, String token) throws Exception;
}
