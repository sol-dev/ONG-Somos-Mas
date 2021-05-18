package com.team32.ong.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.team32.ong.model.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {

	@Query("select body from Comment")
	List<String> findAllByBody();
}
