package com.team32.ong.service.impl;


import com.team32.ong.constant.ConstantMessage;
import com.team32.ong.dto.CategoryDTO;
import com.team32.ong.exception.custom.BadRequestException;
import com.team32.ong.model.Category;
import com.team32.ong.repository.CategoryRepository;
import com.team32.ong.service.CategoryService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryImplService implements CategoryService {

    @Autowired
    private CategoryRepository repo;


    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) throws BadRequestException {

        if (categoryDTO.getName() == null){
            throw new BadRequestException(ConstantMessage.MSG_NAME_BAD_REQUEST);
        }

        Category category = repo.save(dtoToEntity(categoryDTO));

        return entityToDto(category);
    }

    @Override
    public CategoryDTO update(Long id, CategoryDTO categoryDTO) throws Exception {

        try {

            //todo: validar usuario

            if (!repo.existsById(id)){
                throw new NotFoundException(ConstantMessage.MSG_CATEGORY_NOT_FOUND.concat(id.toString()));
            }
            if (categoryDTO.getName() == null || categoryDTO.getName().isBlank()){
                throw new BadRequestException(ConstantMessage.MSG_NAME_BAD_REQUEST);
            }
            if (categoryDTO.getId() != id){
                throw new BadRequestException(ConstantMessage.MSG_ID_DIFFERS);
            }

            repo.save(dtoToEntity(categoryDTO));
            return categoryDTO;

        }catch (Exception e){
            throw new Exception(e.getMessage());
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
