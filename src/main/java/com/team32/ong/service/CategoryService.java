package com.team32.ong.service;

import com.team32.ong.dto.CategoryDTO;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;

@Service
public interface CategoryService {

    CategoryDTO save(CategoryDTO categoryDTO);

    public CategoryDTO findById(final Long id) throws NotFoundException;

}
