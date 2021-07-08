package com.team32.ong.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.team32.ong.component.PaginationComponent;
import com.team32.ong.constant.ConstantExceptionMessage;
import com.team32.ong.dto.NewsDto;
import com.team32.ong.exception.custom.BadRequestException;
import com.team32.ong.model.News;
import com.team32.ong.repository.NewsRepository;
import com.team32.ong.service.NewsService;


@Service
public class NewsServiceImpl implements NewsService {
	
	@Autowired
	NewsRepository newsRepository;

	@Autowired
	private PaginationComponent paginationComponent;

	@Override
	public NewsDto save(NewsDto newsDto) {
		News news = this.dtoToModel(newsDto);
		news.setDeleted(false);
		News newNews = newsRepository.save(news);
		return modelToDto(newNews);
	}

	@Override
	public NewsDto save(NewsDto newsDto, MultipartFile image) throws IOException {
		if(image != null && !image.isEmpty()) {
			try {
				String uniqueFilename = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
	            Path rootPath = Paths.get("upload").resolve(uniqueFilename);
	            Path rootAbsolutepath = rootPath.toAbsolutePath();        
				Files.copy(image.getInputStream(), rootAbsolutepath);
			} catch (IOException e) {
				e.printStackTrace();
				throw new IOException(ConstantExceptionMessage.MSG_IO_Exception);
			}
			newsDto.setImage(image.getOriginalFilename());		
		}
		StringBuffer errorsFound = new StringBuffer();
		if(newsDto.getName().isEmpty()) {			
			errorsFound.append(ConstantExceptionMessage.MSG_NAME_BAD_REQUEST);
		}
		if(newsDto.getContent().isEmpty()) {			
			errorsFound.append(ConstantExceptionMessage.MSG_CONTENT_BAD_REQUEST);
		}
		if(errorsFound.length() > 0) {
			throw new BadRequestException(errorsFound.toString());
		}
		News newsToCreate = this.dtoToModel(newsDto);
		newsToCreate.setDeleted(false);
		News newsCreated = newsRepository.save(newsToCreate);		
		return modelToDto(newsCreated);
	}

	@Override
	public NewsDto update(Long id, NewsDto newsDto) throws NotFoundException {
		if (!newsRepository.existsById(id)){
			throw new NotFoundException(ConstantExceptionMessage.MSG_NOT_FOUND);
		}else if(newsDto.getName().isEmpty()){
			throw new BadRequestException(ConstantExceptionMessage.MSG_NAME_BAD_REQUEST);
		}else if (newsDto.getContent().isEmpty()){
			throw new BadRequestException(ConstantExceptionMessage.MSG_CONTENT_BAD_REQUEST);
		}
		News news = dtoToModel(newsDto);
		news.setId(id);
		return modelToDto(newsRepository.save(news));
	}

	@Override
	public boolean deleteNew(Long id) throws NotFoundException{
		return newsRepository.findById(id).map(news -> {
			newsRepository.delete(news);
			return true;
		}).orElseThrow(() -> new NotFoundException(ConstantExceptionMessage.MSG_ERROR_DELETE_NEWS));
	}



	@Override
	public NewsDto findById(Long id) throws NotFoundException {
		News news = newsRepository.findById(id).orElseThrow(() -> new NotFoundException(
				ConstantExceptionMessage.MSG_NEWS_NOT_FOUND.concat(id.toString())));
    	return modelToDto(news);
	}
	
	@Override
	public String getAll(Pageable page) throws BadRequestException{
		Page<News> newsEntity = newsRepository.findAll(page);
		if(newsEntity.getTotalPages() <= page.getPageNumber()){
			throw new BadRequestException(ConstantExceptionMessage.MSG_PAGE_NOT_FOUND);
		}
		return paginationComponent.changePaginationResponse(newsEntity.map(this::modelToDto));
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
