package com.team32.ong.dto;

import java.util.List;
import java.util.Set;
import com.team32.ong.model.Category;
import com.team32.ong.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsDto{
	private Long id;
	private String name;
	private String content;
	private String image;
	private Set<Category> categories;
	private List<Comment> comments;

}