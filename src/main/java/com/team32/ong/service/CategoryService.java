package com.team32.ong.service;

import com.team32.ong.dto.CategoryDTO;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;

import java.util.List;


@Service
public interface CategoryService {

    CategoryDTO save(CategoryDTO categoryDTO);

    public CategoryDTO findById(final Long id) throws NotFoundException;
    List<String> viewAll() throws Exception;

}
