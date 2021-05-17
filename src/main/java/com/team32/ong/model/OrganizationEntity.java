package com.team32.ong.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.team32.ong.constant.ConstantExceptionMessage;

import java.time.LocalDateTime;

@Entity
@Table(name = "organization")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class OrganizationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = ConstantExceptionMessage.MSG_NAME_BAD_REQUEST)
    @Column(name = "name", nullable = false)
    private String name;

    @NotEmpty(message = ConstantExceptionMessage.MSG_IMAGE_BAD_REQUEST)
    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private Integer phone;

    @Column(name = "email", nullable = false)
    @NotEmpty(message = ConstantExceptionMessage.MSG_EMAIL_BAD_REQUEST)
    @Email(message = ConstantExceptionMessage.MSG_EMAIL_INVALID)
    private String email;

    @Column (name = "welcome_text")
    private String welcomeText;

    @Column(name = "aboutUsText")
    private String aboutUsText;

    @Column(name = "facebookUrl", nullable = true)
    private String facebookUrl;

    @Column(name = "linkedinUrl", nullable = true)
    private String linkedinUrl;

    @Column(name = "instagramUrl", nullable = true)
    private String instagramUrl;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createDate;

    @JsonIgnore
    @UpdateTimestamp
    @Column(name = "last_modified_date")
    private LocalDateTime modifiedDate;

    @Column(name = "deleted", columnDefinition = "boolean default false")
    private Boolean deleted;

    

}
