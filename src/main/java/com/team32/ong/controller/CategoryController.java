package com.team32.ong.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team32.ong.dto.CategoryDTO;
import com.team32.ong.dto.ListCategoryNameDTO;
import com.team32.ong.dto.ModifyCategoryDTO;
import com.team32.ong.exception.ErrorResponse;
import com.team32.ong.exception.custom.BadRequestException;
import com.team32.ong.service.CategoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.NotFoundException;

@Api(value="/api/v1/categories", tags="Category Profile Controller")
@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value="Insert a Category", response=CategoryDTO.class)
    @ApiResponses(value = {
    		@ApiResponse(code = 201, message = "CREATED", response = CategoryDTO.class),
    		@ApiResponse(code = 401, message = "UNAUTHORIZED!", response = ErrorResponse.class),
    		@ApiResponse(code = 403, message = "FORBIDDEN!", response = ErrorResponse.class),
    		@ApiResponse(code = 404, message = "NOT FOUND", response = ErrorResponse.class)
    })
    @PostMapping
    public ResponseEntity<CategoryDTO> save(@RequestBody CategoryDTO categoryDTO) throws BadRequestException {

        return new ResponseEntity<>(categoryService.save(categoryDTO), HttpStatus.CREATED);
          
    }
    
    //only for admin
    @ApiOperation(value="Fetch Category by Id", response=CategoryDTO.class)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "SUCCESS", response = CategoryDTO.class),
    		@ApiResponse(code = 401, message = "UNAUTHORIZED!", response = ErrorResponse.class),
    		@ApiResponse(code = 403, message = "FORBIDDEN!", response = ErrorResponse.class),
    		@ApiResponse(code = 404, message = "NOT FOUND", response = ErrorResponse.class)
    })
    @GetMapping(value = "/id")
    public ResponseEntity<CategoryDTO> findById(@RequestParam("id") Long id) throws NotFoundException{
        return new ResponseEntity<CategoryDTO>(categoryService.findById(id), HttpStatus.OK);
    }
       
    @ApiOperation(value="Soft delete a Category by Id", response=CategoryDTO.class)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "SUCCESS"),
    		@ApiResponse(code = 401, message = "UNAUTHORIZED!", response = ErrorResponse.class),
    		@ApiResponse(code = 403, message = "FORBIDDEN!", response = ErrorResponse.class),
    		@ApiResponse(code = 404, message = "NOT FOUND", response = ErrorResponse.class)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) throws NotFoundException{
    	categoryService.delete(id);
    	return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value="Update the Details of a Category", response=ModifyCategoryDTO.class)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "SUCCESS", response = CategoryDTO.class),
    		@ApiResponse(code = 401, message = "UNAUTHORIZED!", response = ErrorResponse.class),
    		@ApiResponse(code = 403, message = "FORBIDDEN!", response = ErrorResponse.class),
    		@ApiResponse(code = 404, message = "NOT FOUND", response = ErrorResponse.class)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,@RequestBody ModifyCategoryDTO categoryDTO)
            throws Exception {


        CategoryDTO updatedCategory = categoryService.update(id, categoryDTO);


        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @ApiOperation(value="Fetch all Category", response=Iterable.class)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "SUCCESS",  response = CategoryDTO.class),
    		@ApiResponse(code = 401, message = "UNAUTHORIZED!", response = ErrorResponse.class),
    		@ApiResponse(code = 403, message = "FORBIDDEN!", response = ErrorResponse.class),
    		@ApiResponse(code = 404, message = "NOT FOUND", response = ErrorResponse.class)
    })
    @GetMapping
    public ResponseEntity<?> listCategories() throws Exception {

       List<ListCategoryNameDTO> list = categoryService.findAll();
        return new ResponseEntity(list, HttpStatus.OK);

    }

}