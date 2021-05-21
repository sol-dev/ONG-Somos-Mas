package com.team32.ong.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javassist.NotFoundException;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;
import com.team32.ong.dto.SlideDto;
import com.team32.ong.model.Slide;
import com.team32.ong.repository.SlideRepository;
import com.team32.ong.service.SlideService;

@Service
@Transactional
public class SlideServiceImpl implements SlideService {

    @Autowired
    private SlideRepository slideRepository;

    @Override
    public List<SlideDto> slideList() {
        List<Slide> slideList = slideRepository.findAll();
        List<SlideDto> slideDtoList = mapList(slideList, SlideDto.class);
        return slideDtoList;
    }

    public TreeMap<String, TreeMap<Integer, String>> imageAndOrderByOrganization() {
        List<String> organizations = slideRepository.findAllOrganizationsName();
        TreeMap<String, TreeMap<Integer, String>> result = new TreeMap<>();
        for (String name : organizations) {
            List<Slide> slides = slideRepository.findAllSlidesByOrganizationName(name);
            TreeMap<Integer, String> imageOrder = new TreeMap<>();
            for (Slide s : slides) {
                imageOrder.put(s.getOrder(), s.getImageUrl());
            }
            result.put(name, imageOrder);
        }
        return result;
    }

    @Override
    public List<String> getOrganizationSlides(Long id) throws NotFoundException {
        return slideRepository.findAllSlideUrlByOrganizationId(id);
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

    public static <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        ModelMapper mapper = new ModelMapper();
        return source.stream().map(element -> mapper.map(element, targetClass)).collect(Collectors.toList());
    }

}
