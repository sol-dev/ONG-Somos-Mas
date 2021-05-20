package com.team32.ong.service.impl;


import java.util.Optional;

import com.team32.ong.constant.ConstantExceptionMessage;
import com.team32.ong.dto.CategoryDTO;
import com.team32.ong.exception.custom.BadRequestException;
import com.team32.ong.model.Category;
import com.team32.ong.repository.CategoryRepository;
import com.team32.ong.service.CategoryService;
import com.team32.ong.dto.ModifyCategoryDTO;

import javassist.NotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class CategoryImplService implements CategoryService {

    @Autowired
    private CategoryRepository repo;

    private static final Logger logger = LoggerFactory.getLogger(CategoryImplService.class);


    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) throws BadRequestException {

        if (categoryDTO.getName() == null){
            throw new BadRequestException(ConstantExceptionMessage.MSG_NAME_BAD_REQUEST);
        }

        Category category = repo.save(dtoToEntity(categoryDTO));

        return entityToDto(category);
    }

    @Override
    public CategoryDTO update(Long id, ModifyCategoryDTO categoryDTO) throws
            NotFoundException, BadRequestException {


            //todo: validar usuario

            Category oldcategory = repo.findById(id).orElse(null);
            if (oldcategory == null){
                throw new NotFoundException(ConstantExceptionMessage.MSG_CATEGORY_NOT_FOUND.concat(id.toString()));
            }
            if (categoryDTO.getName() == null || categoryDTO.getName() == ""){
                throw new BadRequestException(ConstantExceptionMessage.MSG_NAME_BAD_REQUEST);
            }
            if (categoryDTO.getDescription() == null || categoryDTO.getDescription() == ""){
                throw new BadRequestException(ConstantExceptionMessage.MSG_DESCRIPTION_EMPTY);
            }

            oldcategory = modifyDtoToEntitity(categoryDTO);
            oldcategory.setId(id);

            repo.save((oldcategory));
            return entityToDto(oldcategory);


    }

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


    //model mapper
    private Category dtoToEntity(CategoryDTO categoryDto){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(categoryDto, Category.class);
    }

    private CategoryDTO entityToDto(Category category){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(category, CategoryDTO.class);
    }

    private Category modifyDtoToEntitity(ModifyCategoryDTO categoryDTO){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(categoryDTO, Category.class);
    }

    private ModifyCategoryDTO entityToModifyDto(Category category){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(category, ModifyCategoryDTO.class);
    }
}
