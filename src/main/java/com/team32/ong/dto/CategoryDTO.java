package com.team32.ong.dto;

import com.team32.ong.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;


@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CategoryDTO {

    @Autowired
    private ModelMapper mapper;

    private Long id;
    private String name;
    private String description;
    private String image;


    public Category convertToEntity(CategoryDTO categoryDTO){
        return mapper.map(categoryDTO, Category.class);
    }
}
