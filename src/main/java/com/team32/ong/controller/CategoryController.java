package com.team32.ong.controller;

import com.team32.ong.dto.CategoryDTO;
import com.team32.ong.model.Category;
import com.team32.ong.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDTO> save(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult result){

        Category category = dtoToEntity(categoryDTO);

        if (result.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            Category categorySave = categoryService.save(category);
            return new ResponseEntity<>(entityToDto(categorySave), HttpStatus.CREATED);
        }
    }

    private Category dtoToEntity(CategoryDTO categoryDto){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(categoryDto, Category.class);
    }

    private CategoryDTO entityToDto(Category category){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(category, CategoryDTO.class);
    }
}