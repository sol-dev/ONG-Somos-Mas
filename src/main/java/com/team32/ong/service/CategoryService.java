package com.team32.ong.service;

import com.team32.ong.dto.CategoryDTO;

import javassist.NotFoundException;

import org.springframework.stereotype.Service;
import com.team32.ong.dto.ModifyCategoryDTO;
import com.team32.ong.dto.ListCategoryNameDTO;
import java.util.List;

@Service
public interface CategoryService {

    CategoryDTO save(CategoryDTO categoryDTO);
    void delete(Long id) throws NotFoundException;
    public CategoryDTO findById(final Long id) throws NotFoundException;
    CategoryDTO update(Long id, ModifyCategoryDTO categoryDTO) throws Exception;
    List<ListCategoryNameDTO> findAll() throws Exception;
}
