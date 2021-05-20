package com.team32.ong.service;

import org.springframework.stereotype.Service;

import com.team32.ong.dto.SlideDto;
import com.team32.ong.model.Slide;

import javassist.NotFoundException;

@Service
public interface SlideService {
	
	SlideDto findById(Long id) throws NotFoundException;
	SlideDto entityToDto(Slide slide);

}
