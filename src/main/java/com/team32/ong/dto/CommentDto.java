package com.team32.ong.dto;

import com.team32.ong.model.News;
import com.team32.ong.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto{
	
	private long id;
	private String body;
	private UserDTOResponse user;
	private News news;

}
