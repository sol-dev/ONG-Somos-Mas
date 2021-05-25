package com.team32.ong.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.team32.ong.constant.ConstantExceptionMessage;
import com.team32.ong.dto.CommentDTOByIdNews;

import javassist.NotFoundException;

@SpringBootTest
public class CommentServiceTest {

	@Autowired
	private CommentService commentService;
	
	@Test
	public void findCommentsByNewsId_withNewsIdExist_returnAListComment() throws NotFoundException {
		Long newsId = 1L;
		
		List<CommentDTOByIdNews> listFound = commentService.getCommentsByNewsId(newsId);
		
		assertThat(listFound).isNotEmpty();
		assertEquals(3, listFound.size());
	}
	
	@Test
	public void findCommentsByNewsId_withDniNonexistent_returnNotFoundException(){	
		Long newsId = -99L;
		Exception exception = assertThrows(NotFoundException.class, () -> {
			commentService.getCommentsByNewsId(newsId);
	    });
		
		 String expectedMessage = ConstantExceptionMessage.MSG_NEWS_NOT_FOUND + newsId;
		 String actualMessage = exception.getMessage();
		 assertTrue(actualMessage.contains(expectedMessage));
	}
}