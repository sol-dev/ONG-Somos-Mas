package com.team32.ong.service.impl;

import com.team32.ong.component.AmazonClient;
import com.team32.ong.dto.OrganizationDTO;
import com.team32.ong.dto.OrganizationPublicDTO;
import com.team32.ong.dto.SlideDtoRequest;
import com.team32.ong.repository.IOrganizationRepository;
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
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class SlideServiceImpl implements SlideService {
	
	@Autowired
	private SlideRepository slideRepository;

	@Autowired
	private AmazonClient amazonClient;

	@Autowired
    private IOrganizationRepository organizationRepository;

	@Override
	public SlideDto findById(Long id) throws NotFoundException{
		Slide slideFound = slideRepository
                .findById(id)
                .orElseThrow(()-> new NotFoundException(ConstantExceptionMessage.MSG_NOT_FOUND + id));
		return modelToDto(slideFound);
	}

    @Override
    public List<SlideDto> slideList() {
        List<Slide> slideList = slideRepository.findAll();
        return slideList.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public SlideDto save(SlideDtoRequest slideDtoRequest, MultipartFile file, Long idOrganization) throws Throwable{
        Slide slide = dtoRequestToModel(slideDtoRequest);
        slide.setImageUrl(amazonClient.uplodFileToS3Bucket(file).getBody());
        slide.setOrganization(organizationRepository
                .findById(idOrganization).orElseThrow(()-> new NotFoundException(ConstantExceptionMessage.MSG_ORGANIZATION_NOT_FOUD)));

        return modelToDto(slideRepository.save(slide));
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
        mapper.map(slide.getOrganization(), OrganizationPublicDTO.class);
        return mapper.map(slide, SlideDto.class);
    }

    private Slide dtoToModel(SlideDto slideDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(slideDto, Slide.class);
    }
    private Slide dtoRequestToModel(SlideDtoRequest slideDtoRequest) {
	    ModelMapper mapper = new ModelMapper();
	    return mapper.map(slideDtoRequest, Slide.class);
    }

}
