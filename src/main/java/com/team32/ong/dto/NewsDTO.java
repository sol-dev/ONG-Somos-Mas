package com.team32.ong.dto;

import java.util.Set;

import com.team32.ong.model.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsDTO {
	
	private Long id;
	private String name;
	private String content;
	private String image;
	private Set<Category> categories;

}