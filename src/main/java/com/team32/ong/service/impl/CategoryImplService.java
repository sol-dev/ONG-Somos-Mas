package com.team32.ong.service.impl;


import java.util.Arrays;
import java.util.Optional;

import com.team32.ong.constant.ConstantExceptionMessage;
import com.team32.ong.dto.CategoryDTO;
import com.team32.ong.dto.ListCategoryNameDTO;
import com.team32.ong.exception.custom.BadRequestException;
import com.team32.ong.model.Category;
import com.team32.ong.repository.CategoryRepository;
import com.team32.ong.service.CategoryService;

import javassist.NotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryImplService implements CategoryService {

    @Autowired
    private CategoryRepository repo;
    @Autowired
    private  ModelMapper modelMapper;


    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) throws BadRequestException {

        if (categoryDTO.getName() == null){
            throw new BadRequestException(ConstantExceptionMessage.MSG_NAME_BAD_REQUEST);
        }

        Category category = repo.save(dtoToEntity(categoryDTO));

        return entityToDto(category);
    }

    @Override
	public void delete(Long id) throws NotFoundException {
		boolean categoryExists = repo.existsById(id);
		if(!categoryExists) {
			throw new NotFoundException(ConstantExceptionMessage.MSG_NOT_FOUND + id);
		}
		repo.deleteById(id);
	}
    
    public CategoryDTO findById(Long id) throws NotFoundException{
        Optional<Category> category = repo.findById(id) ;
        if(!category.isPresent()){
            throw new NotFoundException(ConstantExceptionMessage.MSG_NOT_FOUND+id);
        }
        return entityToDto(category.get());
    }

    @Override
    public List<ListCategoryNameDTO> findAll() throws Exception {
       List<Category> lCategory = repo.findAll();
       List<ListCategoryNameDTO> category = Arrays.asList(modelMapper.map(lCategory,
               ListCategoryNameDTO[].class));
       return category;
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
