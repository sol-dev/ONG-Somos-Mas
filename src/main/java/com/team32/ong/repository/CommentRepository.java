package com.team32.ong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.team32.ong.model.Comment;
import com.team32.ong.model.User;

public interface CommentRepository extends JpaRepository<Comment,Long> {
	
	@Query("select c.user from Comment c where c.id = ?1")
	public User findByUserId(Long id);
}
