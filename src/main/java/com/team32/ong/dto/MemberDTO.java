package com.team32.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

	private Long memberId;
	private String name;
	private String facebookUrl;
	private String instagramUrl;
	private String linkedinUrl;
	private String image;
	private String description;
}
