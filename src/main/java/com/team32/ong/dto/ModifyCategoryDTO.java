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

	@ApiModelProperty(name="name", required=true, value="Here would go the name of the Category", example = "Game")
    private String name;
	@ApiModelProperty(name="description", value="Here the description would go",example = "Here would go the description")
    private String description;
	@ApiModelProperty(name="image", value="Here the url of the image would go", example = "imagen.url")
    private String image;
}
