package com.team32.ong.service.impl;

import com.team32.ong.component.Pagination;
import com.team32.ong.component.Validation;
import com.team32.ong.constant.ConstantExceptionMessage;
import com.team32.ong.dto.TestimonialDto;
import com.team32.ong.exception.custom.BadRequestException;
import com.team32.ong.model.Testimonial;
import com.team32.ong.repository.TestimonialRepository;
import com.team32.ong.service.TestimonialService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class TestimonialServiceImpl implements TestimonialService {

    @Autowired
    private TestimonialRepository testimonialRepository;

    @Autowired
    private Validation validations;

    @Autowired
    private Pagination paginationComponent;

    @Override
    public TestimonialDto save(TestimonialDto testimonialDto) {

        if(testimonialDto.getName().isEmpty() || testimonialDto.getName().length() == 0){
            throw new BadRequestException(ConstantExceptionMessage.MSG_NAME_BAD_REQUEST);
        }

        if(testimonialDto.getContent().isEmpty() || testimonialDto.getContent().length() == 0){
            throw new BadRequestException(ConstantExceptionMessage.MSG_CONTENT_BAD_REQUEST);
        }

        if(testimonialDto.getImage().isEmpty() || testimonialDto.getImage().length() == 0){

            throw new BadRequestException(ConstantExceptionMessage.MSG_IMAGE_BAD_REQUEST);
        }

        if(validations.stringHasDigit(testimonialDto.getName())){
            throw new BadRequestException(ConstantExceptionMessage.MSG_NAME_NOT_NUMBER);
        }

        if(validations.stringIsOnlyDigits(testimonialDto.getContent())){
            throw new BadRequestException(ConstantExceptionMessage.MSG_CONTENT_NOT_NUMBER);
        }

        Testimonial testimonialToCreate = this.dtoToModel(testimonialDto);

        testimonialToCreate.setDeleted(false);
        Testimonial testimonialCreated = testimonialRepository.save(testimonialToCreate);

        return modelToDto(testimonialCreated);
    }

    @Override
    public TestimonialDto updateById(TestimonialDto testimonialDtoToUpdate, Long id) throws NotFoundException {

        if(!testimonialRepository.existsById(id)){
            throw new NotFoundException( ConstantExceptionMessage.MSG_NOT_FOUND + id);
        }

        Optional<Testimonial> testimonials = testimonialRepository.findById(id);

        Testimonial testimonialToUpdate = testimonials.get();

        //validate if null
        testimonialDtoToUpdate = setNullsAttributes(testimonialDtoToUpdate, testimonialToUpdate);

        //validate if empty
        if(testimonialDtoToUpdate.getName().isEmpty()){
            throw new BadRequestException(ConstantExceptionMessage.MSG_NAME_BAD_REQUEST);
        }

        if(testimonialDtoToUpdate.getContent().isEmpty()){
            throw new BadRequestException(ConstantExceptionMessage.MSG_CONTENT_BAD_REQUEST);
        }

        if(testimonialDtoToUpdate.getImage().isEmpty()){
            throw new BadRequestException(ConstantExceptionMessage.MSG_IMAGE_BAD_REQUEST);
        }

        //validate if has a digit
        if(validations.stringHasDigit(testimonialDtoToUpdate.getName())){
            throw new BadRequestException(ConstantExceptionMessage.MSG_NAME_NOT_NUMBER);
        }
        //validate if only digit
        if(validations.stringIsOnlyDigits(testimonialDtoToUpdate.getContent())){
            throw new BadRequestException(ConstantExceptionMessage.MSG_CONTENT_NOT_NUMBER);
        }

        //set testimonial's attributes
        testimonialToUpdate.setName(testimonialDtoToUpdate.getName());
        testimonialToUpdate.setImage(testimonialDtoToUpdate.getImage());
        testimonialToUpdate.setContent(testimonialDtoToUpdate.getContent());

        //save testimonial
        testimonialRepository.save(testimonialToUpdate);

        return modelToDto(testimonialToUpdate);
    }

    @Override
    public void deleteById(Long id) throws NotFoundException {
        if(!testimonialRepository.existsById(id)){
            throw new NotFoundException(ConstantExceptionMessage.MSG_NOT_FOUND + id);
        }
        testimonialRepository.deleteById(id);
    }

    @Override
    public String getTestimonials(Pageable page) {
        Page<Testimonial> testimonials = testimonialRepository.findAll(page);
        Page<TestimonialDto> testimonialDtos = testimonials.map(this::modelToDto);

        return paginationComponent.changePaginationResponse(testimonialDtos);
    }

    private TestimonialDto setNullsAttributes(TestimonialDto dto, Testimonial model){
        if(dto.getName() == null){
            dto.setName(model.getName());
        }

        if(dto.getContent() == null){
            dto.setContent(model.getContent());
        }

        if(dto.getImage() == null){
            dto.setImage(model.getImage());
        }

        return dto;
    }

    private TestimonialDto modelToDto(Testimonial testimonial){

        ModelMapper mapper = new ModelMapper();
        TestimonialDto map = mapper.map(testimonial, TestimonialDto.class);

        return map;
    }


    private Testimonial dtoToModel(TestimonialDto testimonialDto){

        ModelMapper mapper = new ModelMapper();
        Testimonial map = mapper.map(testimonialDto, Testimonial.class);

        return map;
    }
}
