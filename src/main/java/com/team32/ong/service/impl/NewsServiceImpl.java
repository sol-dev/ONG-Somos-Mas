package com.team32.ong.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team32.ong.dto.NewsDTO;
import com.team32.ong.mapper.NewsMapper;
import com.team32.ong.model.News;
import com.team32.ong.repository.NewsRepository;
import com.team32.ong.service.NewsService;

@Service
public class NewsServiceImpl implements NewsService{
	
	@Autowired
	private NewsRepository newsRepository;
	
	@Autowired
	private NewsMapper newsMapper;

	@Override
	public News save(NewsDTO newsDTO) {
		News news = newsMapper.toNews(newsDTO);
		news.setDeleted(false);
		return newsRepository.save(news);
	}

}