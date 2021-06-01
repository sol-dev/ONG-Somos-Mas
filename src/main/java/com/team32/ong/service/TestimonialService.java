package com.team32.ong.service;


import com.team32.ong.dto.TestimonialDto;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TestimonialService {
    TestimonialDto save(TestimonialDto testimonialDto);
    TestimonialDto updateById(TestimonialDto testimonialDto, Long id) throws NotFoundException;
    void deleteById(Long id) throws NotFoundException;
    String getTestimonials(Pageable page) throws NotFoundException;
}
