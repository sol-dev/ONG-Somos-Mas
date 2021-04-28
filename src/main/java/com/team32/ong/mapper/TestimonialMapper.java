package com.team32.ong.mapper;

import com.team32.ong.dto.TestimonialDto;
import com.team32.ong.model.Testimonial;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TestimonialMapper {

    public TestimonialDto modelToDto(Testimonial testimonial){

        ModelMapper mapper = new ModelMapper();
        TestimonialDto map = mapper.map(testimonial, TestimonialDto.class);

        return map;
    }

    public Testimonial dtoToEntity(TestimonialDto testimonialDto){

        ModelMapper mapper = new ModelMapper();
        Testimonial map = mapper.map(testimonialDto, Testimonial.class);

        return map;
    }
}
