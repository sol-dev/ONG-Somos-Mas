package com.team32.ong.service;

import com.team32.ong.dto.CategoryDTO;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {

    public CategoryDTO save(CategoryDTO categoryDTO);
}
