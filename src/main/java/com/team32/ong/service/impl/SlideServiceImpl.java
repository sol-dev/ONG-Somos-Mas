package com.team32.ong.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team32.ong.constant.ConstantExceptionMessage;
import com.team32.ong.dto.SlideDto;
import com.team32.ong.model.Slide;
import com.team32.ong.repository.SlideRepository;
import com.team32.ong.service.SlideService;

import javassist.NotFoundException;

@Service
@Transactional
public class SlideServiceImpl implements SlideService {
	
	@Autowired
	private SlideRepository slideRepo;

	@Override
	public SlideDto findById(Long id) throws NotFoundException{
		Optional<Slide> slideFound = slideRepo.findById(id);
		if(!slideFound.isPresent()) {
			throw new NotFoundException(ConstantExceptionMessage.MSG_NOT_FOUND + id);
		}
		return entityToDto(slideFound.get());
	}

	@Override
    public SlideDto entityToDto(Slide slide){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(slide, SlideDto.class);
    }

}
