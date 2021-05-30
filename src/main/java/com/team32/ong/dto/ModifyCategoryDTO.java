package com.team32.ong.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("Modify Categoty Model")
public class ModifyCategoryDTO {

	@ApiModelProperty(name="name", required=true, value="Here would go the name of the Category")
    private String name;
	@ApiModelProperty(name="description", value="Here would go the description")
    private String description;
	@ApiModelProperty(name="image", value="Here would go the image")
    private String image;
}
