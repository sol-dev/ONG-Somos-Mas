package com.team32.ong.service;

import com.team32.ong.dto.CategoryAdminDTO;
import com.team32.ong.dto.CategoryDTO;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {

    CategoryDTO save(CategoryDTO categoryDTO);

    public CategoryAdminDTO findById(final Long id);

}
