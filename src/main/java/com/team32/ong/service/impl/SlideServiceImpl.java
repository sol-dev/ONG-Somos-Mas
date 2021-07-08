package com.team32.ong.service.impl;


import java.util.Optional;

import com.team32.ong.component.AmazonClient;
import com.team32.ong.constant.ConstantPathImage;
import com.team32.ong.dto.OrganizationPublicDTO;
import com.team32.ong.dto.SlideDtoRequest;
import com.team32.ong.exception.custom.BadRequestException;
import com.team32.ong.repository.IOrganizationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javassist.NotFoundException;
import com.team32.ong.constant.ConstantExceptionMessage;
import java.util.List;
import java.util.TreeMap;
import com.team32.ong.dto.SlideDto;
import com.team32.ong.model.Slide;
import com.team32.ong.repository.SlideRepository;
import com.team32.ong.service.SlideService;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class SlideServiceImpl implements SlideService {


	@Autowired
    private IOrganizationRepository organizationRepository;
    @Autowired
    private SlideRepository slideRepository;
    @Autowired
    private AmazonClient amazonClient;


	@Override
	public SlideDto findById(Long id) throws NotFoundException{
		Slide slideFound = slideRepository
                .findById(id)
                .orElseThrow(()-> new NotFoundException(ConstantExceptionMessage.MSG_NOT_FOUND + id));
		return modelToDto(slideFound);
	}

    @Override
    public SlideDto save(SlideDtoRequest slideDtoRequest, Long idOrganization) throws Throwable{

	    if (slideDtoRequest.getText().isEmpty()){
	        throw new BadRequestException(ConstantExceptionMessage.MSG_TEXT_BAD_REQUEST);
        }else if (slideDtoRequest.getOrder() == null){
            throw new BadRequestException(ConstantExceptionMessage.MSG_ORDER_BAD_REQUEST);
        }

        Slide slide = dtoRequestToModel(slideDtoRequest);
        slide.setImageUrl(ConstantPathImage.SLIDE_IMAGE);
        slide.setOrganization(organizationRepository
                .findById(idOrganization).orElseThrow(()-> new NotFoundException(ConstantExceptionMessage.MSG_ORGANIZATION_NOT_FOUD)));

        return modelToDto(slideRepository.save(slide));
    }

    @Override
    public SlideDto updateImage(MultipartFile multipartFile, Long id) throws Throwable {

	    Slide slide = slideRepository.findById(id).orElseThrow(()-> new NotFoundException(ConstantExceptionMessage.MSG_NOT_FOUND));
	    slide.setImageUrl(amazonClient.uplodFileToS3Bucket(multipartFile).getBody());

        return modelToDto(slideRepository.save(slide));
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

    public List<String> getOrganizationSlides(Long id) throws NotFoundException {
        if (organizationRepository.findById(id).orElse(null) == null) {
            throw new NotFoundException(ConstantExceptionMessage.MSG_NOT_FOUND + id);
        }
        return slideRepository.findAllSlideUrlByOrganizationId(id);
    }

    @Override
    public void deleteSlide(Long id) throws Throwable {
        Slide slide = slideRepository.findById(id).orElseThrow(() -> new NotFoundException(
                ConstantExceptionMessage.MSG_SLIDE_NOT_FOUND.concat(id.toString())));

        if (amazonClient.imageExists(slide.getImageUrl())) {
            amazonClient.deleteFileFromS3Bucket(slide.getImageUrl());
        }
        slideRepository.deleteById(id);
    }
    public SlideDto update(Long id, SlideDtoRequest slideDtoRequest) throws NotFoundException {

       Slide oldSlide = slideRepository.findById(id).orElseThrow(() ->new NotFoundException(
                ConstantExceptionMessage.MSG_NOT_FOUND.concat(id.toString())));


        if (!slideDtoRequest.getText().isEmpty()){
            oldSlide.setText(slideDtoRequest.getText());
        }

        if (!slideDtoRequest.getOrder().equals(null) &&
              slideDtoRequest.getOrder() >=0){
            oldSlide.setOrder(slideDtoRequest.getOrder());
        }
        
        slideRepository.save(oldSlide);


        return modelToDto(oldSlide);
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
