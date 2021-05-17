package com.team32.ong.service;

import com.team32.ong.dto.CategoryDTO;
import com.team32.ong.dto.ModifyCategoryDTO;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {

    CategoryDTO save(CategoryDTO categoryDTO);
    CategoryDTO update(Long id, ModifyCategoryDTO categoryDTO) throws Exception;
}
