package com.team32.ong.service;

import org.springframework.stereotype.Service;

import com.team32.ong.dto.CommentDto;

@Service
public interface CommentService {
	
	CommentDto save(CommentDto commentDto);
	CommentDto getOne(Long id);

}
