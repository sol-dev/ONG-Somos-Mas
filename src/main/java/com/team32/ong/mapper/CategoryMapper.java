package com.team32.ong.mapper;

import com.team32.ong.dto.CategoryDTO;
import com.team32.ong.model.Category;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CategoryMapper {

    public Category toCategoy(CategoryDTO categoryDTO){

        ModelMapper mapper = new ModelMapper();

        return mapper.map(categoryDTO, Category.class);
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
