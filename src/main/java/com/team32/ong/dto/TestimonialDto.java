package com.team32.ong.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class TestimonialDto {

    @NotEmpty(message = "Name may not be empty")
    private String name;

    @NotEmpty(message = "Image may not be empty")
    private String image;

    private String content;
}
