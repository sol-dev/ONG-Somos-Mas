package com.team32.ong.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team32.ong.model.Comment;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
	
	List<Comment> findCommentsByNewsId(Long id);
}
