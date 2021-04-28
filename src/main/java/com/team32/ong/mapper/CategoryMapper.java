package com.team32.ong.mapper;

import com.team32.ong.dto.CategoryDTO;
import com.team32.ong.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toCategoy(CategoryDTO categoryDTO){

        return Category
                .builder()
                .id(categoryDTO.getId())
                .name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .image(categoryDTO.getImage())
                .build();
    }

    public CategoryDTO toCategoyDTO(Category categoryDTO){

        return CategoryDTO
                .builder()
                .id(categoryDTO.getId())
                .name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .image(categoryDTO.getImage())
                .build();
    }
}
