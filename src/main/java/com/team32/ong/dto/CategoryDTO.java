package com.team32.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CategoryDTO {

    private Long id;
    private String name;
    private String description;
    private String image;

}
