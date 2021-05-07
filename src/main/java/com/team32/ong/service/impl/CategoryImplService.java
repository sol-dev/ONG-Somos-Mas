package com.team32.ong.service.impl;


import java.util.Optional;
import com.team32.ong.dto.CategoryDTO;
import com.team32.ong.model.Category;
import com.team32.ong.repository.CategoryRepository;
import com.team32.ong.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;

@Service
public class CategoryImplService implements CategoryService {

    @Autowired
    private CategoryRepository repo;


    @Override
    public CategoryDTO save(CategoryDTO categoryDTO){

        Category category = repo.save(dtoToEntity(categoryDTO));

        return entityToDto(category);
    }

    public CategoryDTO findById(Long id) throws NotFoundException{
        Optional<Category> category = repo.findById(id) ;
        if(!category.isPresent()){
            throw new NotFoundException("No existe la categoria con id: "+id);
        }
        return entityToDto(category.get());
    }

    //model mapper
    private Category dtoToEntity(CategoryDTO categoryDto){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(categoryDto, Category.class);
    }

    private CategoryDTO entityToDto(Category category){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(category, CategoryDTO.class);
    }

}
