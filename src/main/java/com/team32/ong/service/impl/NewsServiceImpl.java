package com.team32.ong.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.team32.ong.dto.NewsDto;
import com.team32.ong.model.News;
import com.team32.ong.repository.NewsRepository;
import com.team32.ong.service.NewsService;

@Service
@Transactional
public class NewsServiceImpl implements NewsService {
	
	@Autowired
	NewsRepository newsRepository;

	@Override
	public NewsDto save(NewsDto newsDto) {
		News news = this.dtoToModel(newsDto);
		news.setDeleted(false);
		News newNews = newsRepository.save(news);
		return modelToDto(newNews);
	}

	@Override
	public NewsDto save(NewsDto newsDto, MultipartFile image)throws IOException {

		if(image != null) {
			String uniqueFilename = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            Path rootPath = Paths.get("upload").resolve(uniqueFilename);
            Path rootAbsolutepath = rootPath.toAbsolutePath();
            Files.copy(image.getInputStream(), rootAbsolutepath);
			newsDto.setImage(image.getOriginalFilename());		
		}
		
		News news = this.dtoToModel(newsDto);
		news.setDeleted(false);
		News newNews = newsRepository.save(news);
		
		return modelToDto(newNews);
	}

	
	@Override
	public NewsDto getOne(Long id) {
		News news = newsRepository.getOne(id);
		return modelToDto(news);
	}	
	
	
	public NewsDto modelToDto(News news) {
		ModelMapper mapper = new ModelMapper();
        NewsDto map = mapper.map(news, NewsDto.class);
		return map;
	}

	public News dtoToModel(NewsDto newsDto) {
		ModelMapper mapper = new ModelMapper();
		News map = mapper.map(newsDto, News.class);
		return map;
	}

}

/*

*/