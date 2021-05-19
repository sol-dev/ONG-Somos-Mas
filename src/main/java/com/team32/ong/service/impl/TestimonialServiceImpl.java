package com.team32.ong.service.impl;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
public class TestimonialServiceImpl implements TestimonialService {

    @Autowired
    private TestimonialRepository testimonialRepository;

    @Autowired
    private Validation validations;

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

        if(testimonialDtoToUpdate.getName().isEmpty() || testimonialDtoToUpdate.getName().length() == 0){
            throw new BadRequestException(ConstantExceptionMessage.MSG_NAME_BAD_REQUEST);
        }

        if(testimonialDtoToUpdate.getContent().isEmpty() || testimonialDtoToUpdate.getContent().length() == 0){
            throw new BadRequestException(ConstantExceptionMessage.MSG_CONTENT_BAD_REQUEST);
        }

        if(testimonialDtoToUpdate.getImage().isEmpty() || testimonialDtoToUpdate.getImage().length() == 0){
            throw new BadRequestException(ConstantExceptionMessage.MSG_IMAGE_BAD_REQUEST);
        }

        Optional<Testimonial> testimonials = testimonialRepository.findById(id);

        Testimonial testimonialToUpdate = testimonials.get();
        testimonialToUpdate.setName(testimonialDtoToUpdate.getName());
        testimonialToUpdate.setImage(testimonialDtoToUpdate.getImage());
        testimonialToUpdate.setContent(testimonialDtoToUpdate.getContent());

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
