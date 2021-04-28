package com.team32.ong.controller;

import com.team32.ong.dto.CategoryDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {


    @PostMapping
    public ResponseEntity<CategoryDTO> save(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult result){

        if (result.hasErrors()){
            return new ResponseEntity(categoryDTO, HttpStatus.NOT_ACCEPTABLE);
        }else{
            return new ResponseEntity(categoryDTO, HttpStatus.CREATED);
        }


    }
}