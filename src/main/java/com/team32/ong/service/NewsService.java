package com.team32.ong.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.team32.ong.dto.NewsDto;
import com.team32.ong.model.News;

@Service
public interface NewsService {
	
	NewsDto save(NewsDto newsDto);
	NewsDto getOne(Long id);

	NewsDto save(NewsDto newsDto, MultipartFile image)throws IOException;
	
	NewsDto modelToDto(News news);
	News dtoToModel(NewsDto newsDto);
	
}
