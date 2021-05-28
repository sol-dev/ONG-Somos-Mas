package com.team32.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import javax.validation.constraints.Email;

import com.team32.ong.model.Slide;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDTO {

    private Long id;

    private String name;

    private String image;

    private String address;

    private Integer phone;

    private String email;

    private String welcomeText;

    private String aboutUsText;

    private String facebookUrl;

    private String linkedinUrl;

    private String instagramUrl;

}