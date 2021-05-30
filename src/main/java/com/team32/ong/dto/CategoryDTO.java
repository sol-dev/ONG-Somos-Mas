package com.team32.ong.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Categoty Model")
public class CategoryDTO {

	@ApiModelProperty(name="id", value="Id of the Category")
    private Long id;
	@ApiModelProperty(name="name", required=true, value="Here would go the name of the Category")
    private String name;
	@ApiModelProperty(name="description", value="Here would go the description")
    private String description;
	@ApiModelProperty(name="image", value="Here would go the image")
    private String image;

}
