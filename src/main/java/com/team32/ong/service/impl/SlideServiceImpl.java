package com.team32.ong.service.impl;

import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team32.ong.constant.ConstantExceptionMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.team32.ong.dto.SlideDto;
import com.team32.ong.model.Slide;
import com.team32.ong.repository.SlideRepository;
import com.team32.ong.service.SlideService;

import javassist.NotFoundException;

@Service
@Transactional
public class SlideServiceImpl implements SlideService {
	
	@Autowired
	private SlideRepository slideRepository;

	@Override
	public SlideDto findById(Long id) throws NotFoundException{
		Optional<Slide> slideFound = slideRepository.findById(id);
		if(!slideFound.isPresent()) {
			throw new NotFoundException(ConstantExceptionMessage.MSG_NOT_FOUND + id);
		}
		return modelToDto(slideFound.get());
	}

    @Override
    public List<SlideDto> slideList() {
        List<Slide> slideList = slideRepository.findAll();
        List<SlideDto> slideDtoList = mapList(slideList, SlideDto.class);
        return slideDtoList;
    }

    @Override
    public Map<Integer, String> imageAndOrder() {
        List<SlideDto> slideDtoList = slideList();
        Map<Integer, String> map = new HashMap<>();
        for (SlideDto s : slideDtoList) {
            map.put(s.getOrder(), s.getImageUrl());
        }
        return map;
    }

    private SlideDto modelToDto(Slide slide) {
        ModelMapper mapper = new ModelMapper();
        SlideDto slideDto = mapper.map(slide, SlideDto.class);
        return slideDto;
    }

    private Slide dtoToModel(SlideDto slideDto) {
        ModelMapper mapper = new ModelMapper();
        Slide slide = mapper.map(slideDto, Slide.class);
        return slide;
    }

    private static <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        ModelMapper mapper = new ModelMapper();
        return source.stream().map(element -> mapper.map(element, targetClass)).collect(Collectors.toList());
    }

}
