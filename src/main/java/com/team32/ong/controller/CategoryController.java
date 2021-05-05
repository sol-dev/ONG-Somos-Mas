package com.team32.ong.controller;

import com.team32.ong.dto.CategoryAdminDTO;
import com.team32.ong.dto.CategoryDTO;
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

    /*GET a /categories/:id . Mostrará todos los campos de una categoría en base al id enviado por parámetro.
     En el caso de que no exista, devolverá un error con un código de estado 404 */
    //only for admin
    @GetMapping(value = "/id")
    public ResponseEntity<CategoryAdminDTO> findById(@RequestParam("id") Long id){
        return new ResponseEntity<CategoryAdminDTO>(categoryService.findById(id), HttpStatus.FOUND);
    }

}