package com.team32.ong.service.impl;


import com.team32.ong.dto.CategoryAdminDTO;
import com.team32.ong.dto.CategoryDTO;
import com.team32.ong.model.Category;
import com.team32.ong.repository.CategoryRepository;
import com.team32.ong.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryImplService implements CategoryService {

    @Autowired
    private CategoryRepository repo;


    @Override
    public CategoryDTO save(CategoryDTO categoryDTO){

        Category category = repo.save(dtoToEntity(categoryDTO));

        return entityToDto(category);
    }

    public CategoryAdminDTO findById(Long id){
        return entityToAdminDto(repo.findById(id).get());
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

    private Category dtoToAdminEntity(CategoryAdminDTO dto){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(dto, Category.class);
    }

    private CategoryAdminDTO entityToAdminDto(Category category){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(category, CategoryAdminDTO.class);
    }

}
