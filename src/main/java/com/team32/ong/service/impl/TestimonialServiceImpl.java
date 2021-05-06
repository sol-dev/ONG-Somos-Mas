package com.team32.ong.service.impl;

import com.team32.ong.dto.TestimonialDto;
import com.team32.ong.exception.custom.EmptyInputException;
import com.team32.ong.exception.custom.InvalidDataException;
import com.team32.ong.model.Testimonial;
import com.team32.ong.repository.TestimonialRepository;
import com.team32.ong.service.TestimonialService;
import javassist.NotFoundException;
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

        if(testimonialDto.getContent().isEmpty() || testimonialDto.getContent().length() == 0){
            throw new EmptyInputException("601" ,"Input can not be empty");
        }

        Testimonial testimonialToCreate = this.dtoToModel(testimonialDto);

        testimonialToCreate.setDeleted(false);
        Testimonial testimonialCreated = testimonialRepository.save(testimonialToCreate);

        return modelToDto(testimonialCreated);
    }

    @Override
    public void deleteById(Long id) throws NotFoundException {
        if(!testimonialRepository.existsById(id)){
            throw new NotFoundException("No es posible borrar un testimonio con el id " + id);
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
