package com.team32.ong.dto;

import lombok.Builder;
import lombok.Data;


@Data @Builder
public class CategoryDTO {

    private Long id;
    private String name;
    private String description;
    private String image;

}
