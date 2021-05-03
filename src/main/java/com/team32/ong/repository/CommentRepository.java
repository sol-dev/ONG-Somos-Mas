package com.team32.ong.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team32.ong.model.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {

}
