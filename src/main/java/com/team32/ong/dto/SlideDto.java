package com.team32.ong.dto;

import com.team32.ong.model.OrganizationEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SlideDto {

    private Long id;
	private String imageUrl;
	private String text;
	private int order;
    private OrganizationEntity organization; 

}
