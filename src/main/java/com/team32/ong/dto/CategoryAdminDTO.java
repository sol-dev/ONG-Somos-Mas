package com.team32.ong.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryAdminDTO {

    private Long id;
    private String name;
    private String description;
    private String image;
    private boolean deleted;
    
}
