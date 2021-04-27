package com.team32.ong.controller;

import com.team32.ong.dto.CategoryDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {


    @PostMapping("/save")
    public CategoryDTO save(@Valid CategoryDTO categoryDTO){

        return null;
    }
}