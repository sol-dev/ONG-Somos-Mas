package com.team32.ong.service.impl;

import com.team32.ong.dto.CategoryDTO;
import com.team32.ong.mapper.CategoryMapper;
import com.team32.ong.model.Category;
import com.team32.ong.repository.CategoryRepository;
import com.team32.ong.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryImplService implements CategoryService {

    @Autowired
    private CategoryRepository repo;

    @Autowired
    private CategoryMapper mapper;

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO){
        Category category = categoryDTO.convertToEntity(categoryDTO);

        return mapper.toCategoyDTO(category);
    }



    private Category dtoToEntity(CategoryDTO categoryDTO) {


        ModelMapper mapper = new ModelMapper();
        return mapper.map(categoryDTO, Category.class);
    }
}
