package com.team32.ong.service;

import com.team32.ong.dto.AddCommentBody;
import com.team32.ong.dto.CommentDto;
import com.team32.ong.model.Comment;

import java.util.Optional;

public interface CommentService {

 AddCommentBody update(Long id, AddCommentBody commentDto) throws Exception;
 boolean existsById(Long id) throws Exception;
 Optional<Comment> findById(Long id) throws Exception;
}
