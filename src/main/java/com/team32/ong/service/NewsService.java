package com.team32.ong.service;

import org.springframework.web.multipart.MultipartFile;

import com.team32.ong.dto.NewsDto;

public interface NewsService {
	
	public NewsDto save(NewsDto newsDto, MultipartFile image) throws Exception;

}