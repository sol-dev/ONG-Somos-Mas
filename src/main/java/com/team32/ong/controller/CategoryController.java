package com.team32.ong.controller;


import com.team32.ong.constant.ConstantMessage;
import com.team32.ong.dto.CategoryDTO;
import com.team32.ong.exception.custom.BadRequestException;
import com.team32.ong.exception.custom.InvalidDataException;
import com.team32.ong.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javassist.NotFoundException;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDTO> save(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult result){


        if (result.hasErrors()){

            throw new InvalidDataException(result);

        }else {

            return new ResponseEntity<>(categoryService.save(categoryDTO), HttpStatus.CREATED);

        }
        
    }

    //only for admin
    @GetMapping(value = "/id")
    public ResponseEntity<CategoryDTO> findById(@RequestParam("id") Long id) throws NotFoundException{
        return new ResponseEntity<CategoryDTO>(categoryService.findById(id), HttpStatus.OK);
    }

}