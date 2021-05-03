package com.team32.ong.mapper;

import org.springframework.stereotype.Component;

import com.team32.ong.dto.NewsDto;
import com.team32.ong.model.News;

@Component
public class NewsMapper {
	
	public News toNews(NewsDto newsDTO){
		return News
				.builder()
                .id(newsDTO.getId())
                .name(newsDTO.getName())
                .content(newsDTO.getContent())
                .image(newsDTO.getImage())
                .categories(newsDTO.getCategories())
                .build();
    }

	public NewsDto toNewsDTO(News news){

        return NewsDto
                .builder()
                .id(news.getId())
                .name(news.getName())
                .content(news.getContent())
                .image(news.getImage())
                .categories(news.getCategories())
                .build();
    }
}