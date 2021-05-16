package com.team32.ong.service;

import java.io.IOException;

import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.team32.ong.dto.NewsDto;
import com.team32.ong.model.News;

@Service
public interface NewsService {
	
	NewsDto save(NewsDto newsDto);
	NewsDto getOne(Long id);
	NewsDto findById(Long id);

	NewsDto save(NewsDto newsDto, MultipartFile image)throws IOException;

	boolean deleteNew(Long id) throws NotFoundException;

	NewsDto modelToDto(News news);
	News dtoToModel(NewsDto newsDto);
	
}
