package com.team32.ong.service;

import java.io.IOException;

import javassist.NotFoundException;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.team32.ong.dto.NewsDto;
import com.team32.ong.exception.custom.BadRequestException;
import com.team32.ong.model.News;

import javassist.NotFoundException;

@Service
public interface NewsService {
	
	NewsDto save(NewsDto newsDto);
	NewsDto findById(Long id) throws NotFoundException;

	NewsDto save(NewsDto newsDto, MultipartFile image)throws IOException;
	NewsDto update(Long id, NewsDto newsDto) throws NotFoundException;
	boolean deleteNew(Long id) throws NotFoundException;
	public String getAll(Pageable page) throws BadRequestException;

	NewsDto modelToDto(News news);
	News dtoToModel(NewsDto newsDto);
	
}
