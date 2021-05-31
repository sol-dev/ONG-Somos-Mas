package com.team32.ong.service.impl;

import java.util.Optional;

import com.team32.ong.component.AmazonClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javassist.NotFoundException;
import com.team32.ong.constant.ConstantExceptionMessage;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;
import com.team32.ong.dto.SlideDto;
import com.team32.ong.model.Slide;
import com.team32.ong.repository.IOrganizationRepository;
import com.team32.ong.repository.SlideRepository;
import com.team32.ong.service.SlideService;

@Service
@Transactional
public class SlideServiceImpl implements SlideService {

    @Autowired
    private SlideRepository slideRepository;
    @Autowired
    private AmazonClient amazonClient;

    @Override
    public SlideDto findById(Long id) throws NotFoundException {
        Optional<Slide> slideFound = slideRepository.findById(id);
        if (!slideFound.isPresent()) {
            throw new NotFoundException(ConstantExceptionMessage.MSG_NOT_FOUND + id);
        }
        return modelToDto(slideFound.get());
    }

    @Autowired
    private IOrganizationRepository organizationRepository;

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
        if (organizationRepository.findById(id).orElse(null) == null) {
            throw new NotFoundException(ConstantExceptionMessage.MSG_NOT_FOUND + id);
        }
        return slideRepository.findAllSlideUrlByOrganizationId(id);
    }

    @Override
    public void deleteSlide(Long id) throws Throwable {
        Slide slide = slideRepository.findById(id).orElseThrow(() ->
                new NotFoundException(ConstantExceptionMessage.MSG_NOT_FOUND.concat(id.toString())));

        if(amazonClient.imageExists(slide.getImageUrl())){
            amazonClient.deleteFileFromS3Bucket(slide.getImageUrl());
        }
        slideRepository.deleteById(id);
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
