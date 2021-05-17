package com.team32.ong.service;

import com.team32.ong.dto.CategoryDTO;
import com.team32.ong.dto.ListCategoryNameDTO;
import com.team32.ong.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    CategoryDTO save(CategoryDTO categoryDTO);
    List<String> viewAll() throws Exception;
}
