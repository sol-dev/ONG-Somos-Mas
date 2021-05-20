package com.team32.ong.controller;


import com.team32.ong.dto.CategoryDTO;
import com.team32.ong.dto.ModifyCategoryDTO;
import com.team32.ong.exception.custom.BadRequestException;
import com.team32.ong.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import com.team32.ong.dto.ListCategoryNameDTO;


import javassist.NotFoundException;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDTO> save(@RequestBody CategoryDTO categoryDTO) throws BadRequestException {

        return new ResponseEntity<>(categoryService.save(categoryDTO), HttpStatus.CREATED);
          
    }

    //only for admin
    @GetMapping(value = "/id")
    public ResponseEntity<CategoryDTO> findById(@RequestParam("id") Long id) throws NotFoundException{
        return new ResponseEntity<CategoryDTO>(categoryService.findById(id), HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) throws NotFoundException{
    	categoryService.delete(id);
    	return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,@RequestBody ModifyCategoryDTO categoryDTO)
            throws Exception {


        CategoryDTO updatedCategory = categoryService.update(id, categoryDTO);


        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> listCategories() throws Exception {

       List<ListCategoryNameDTO> list = categoryService.findAll();
        return new ResponseEntity(list, HttpStatus.OK);

    }

}