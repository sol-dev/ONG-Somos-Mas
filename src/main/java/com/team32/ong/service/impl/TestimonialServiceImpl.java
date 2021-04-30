package com.team32.ong.service.impl;

import com.team32.ong.dto.TestimonialDto;
import com.team32.ong.exception.EmptyInputException;
import com.team32.ong.model.Testimonial;
import com.team32.ong.repository.TestimonialRepository;
import com.team32.ong.service.TestimonialService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class TestimonialServiceImpl implements TestimonialService {

    @Autowired
    private TestimonialRepository testimonialRepository;

    @Override
    public TestimonialDto save(TestimonialDto testimonialDto) {

        if(testimonialDto.getName().isEmpty() || testimonialDto.getName().length() == 0 ||
                testimonialDto.getImage().length() == 0 || testimonialDto.getImage().isEmpty()){
            throw new EmptyInputException("601", "Input Fields are empty");
        }

        Testimonial testimonialToSave =this.dtoToEntity(testimonialDto);
        testimonialToSave.setDeleted(false);

        Testimonial testimonial = testimonialRepository.save(testimonialToSave);

        TestimonialDto testimonialDtoSaved = this.modelToDto(testimonial);

        return testimonialDtoSaved;
    }

    private TestimonialDto modelToDto(Testimonial testimonial){

        ModelMapper mapper = new ModelMapper();
        TestimonialDto map = mapper.map(testimonial, TestimonialDto.class);

        return map;
    }

    private Testimonial dtoToEntity(TestimonialDto testimonialDto){

        ModelMapper mapper = new ModelMapper();
        Testimonial map = mapper.map(testimonialDto, Testimonial.class);

        return map;
    }
}
