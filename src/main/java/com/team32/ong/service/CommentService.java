package com.team32.ong.service;

import com.team32.ong.dto.CommentDto;
import com.team32.ong.model.Comment;

public interface CommentService {
	
	public void save(Comment comment);
	public CommentDto modelToDto(Comment comment);
	public Comment dtoToModel(CommentDto commentDto);

}
