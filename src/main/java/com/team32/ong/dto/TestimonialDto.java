package com.team32.ong.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TestimonialDto {
    @ApiModelProperty(value = "the testimonial's name", required = true)
    private String name;

    @ApiModelProperty(value = "the testimonial's image", required = true)
    private String image;

    @ApiModelProperty(value = "the testimonial's content", required = true)
    private String content;
}
