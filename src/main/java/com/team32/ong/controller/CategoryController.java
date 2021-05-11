package com.team32.ong.controller;

import com.team32.ong.dto.CategoryDTO;
import com.team32.ong.exception.custom.BadRequestException;
import com.team32.ong.exception.custom.InvalidDataException;
import com.team32.ong.service.CategoryService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDTO> save(@RequestBody CategoryDTO categoryDTO) throws BadRequestException {

        return new ResponseEntity<>(categoryService.save(categoryDTO), HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<?> listCategories() throws Exception {

        Map<String,Object> response = new HashMap<>();
        List<String> categories = null;

        categories = categoryService.viewAll();

        response.put("categories", categories);

        return new ResponseEntity(response, HttpStatus.OK);
    }

}