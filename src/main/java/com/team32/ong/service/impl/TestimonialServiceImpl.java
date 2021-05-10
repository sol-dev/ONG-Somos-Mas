package com.team32.ong.service.impl;

import com.team32.ong.constant.ConstantMessage;
import com.team32.ong.dto.TestimonialDto;
import com.team32.ong.exception.custom.EmptyInputException;
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

    @Override
    public TestimonialDto save(TestimonialDto testimonialDto) {

        if(testimonialDto.getName().isEmpty() || testimonialDto.getName().length() == 0){
            throw new EmptyInputException("Debe completar el campo nombre");
        }

        if(testimonialDto.getContent().isEmpty() || testimonialDto.getContent().length() == 0){
            throw new EmptyInputException("Debe completar el campo contenido");
        }

        if(testimonialDto.getImage().isEmpty() || testimonialDto.getImage().length() == 0){
            throw new EmptyInputException("Debe completar el campo imagen");
        }

        Testimonial testimonialToCreate = this.dtoToModel(testimonialDto);

        testimonialToCreate.setDeleted(false);
        Testimonial testimonialCreated = testimonialRepository.save(testimonialToCreate);

        return modelToDto(testimonialCreated);
    }

    @Override
    public TestimonialDto updateById(TestimonialDto testimonialDtoToUpdate, Long id) throws NotFoundException {

        if(!testimonialRepository.existsById(id)){
            throw new NotFoundException("No es posible actualizar un testimonio con el id " + id);
        }

        if(testimonialDtoToUpdate.getName().isEmpty() || testimonialDtoToUpdate.getName().length() == 0){
            throw new EmptyInputException("Debe completar el campo nombre");
        }

        if(testimonialDtoToUpdate.getContent().isEmpty() || testimonialDtoToUpdate.getContent().length() == 0){
            throw new EmptyInputException("Debe completar el campo contenido");
        }

        if(testimonialDtoToUpdate.getImage().isEmpty() || testimonialDtoToUpdate.getImage().length() == 0){
            throw new EmptyInputException("Debe completar el campo imagen");
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
            throw new NotFoundException(ConstantMessage.MSG_NOT_FOUND + id);
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
