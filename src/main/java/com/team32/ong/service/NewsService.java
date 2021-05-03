package com.team32.ong.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.team32.ong.dto.NewsDto;

public interface NewsService {
	
	NewsDto save(NewsDto newsDto, MultipartFile image)throws IOException;

}
