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

	@ApiModelProperty(notes="Id of the Category", name="id", value="1")
    private Long id;
	@ApiModelProperty(notes="Name of the Category", name="name", required=true, value="Image")
    private String name;
	@ApiModelProperty(notes="Description of the Category", name="description", value="Here would go the description")
    private String description;
	@ApiModelProperty(notes="Image of the Category", name="image", value="Here would go the image")
    private String image;

}
