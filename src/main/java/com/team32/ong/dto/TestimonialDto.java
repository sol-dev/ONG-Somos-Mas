package com.team32.ong.dto;

import com.team32.ong.constant.ConstantSwaggerMessage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TestimonialDto {
    @ApiModelProperty(value = ConstantSwaggerMessage.MSG_TESTIMONIALDTO_NAME_VALUE, required = true)
    private String name;

    @ApiModelProperty(value = ConstantSwaggerMessage.MSG_TESTIMONIALDTO_IMAGE_VALUE)
    private String image;

    @ApiModelProperty(value = ConstantSwaggerMessage.MSG_TESTIMONIALDTO_CONTENT_VALUE, required = true)
    private String content;
}
