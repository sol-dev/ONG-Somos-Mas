package com.team32.ong.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="member")
public class Member extends Auditable<Date>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberId;

    @NotEmpty
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "facebookUrl", nullable = true)
    private String facebookUrl;

    @Column(name = "instagramUrl", nullable = true)
    private String instagramUrl;

    @Column(name = "linkedinUrl", nullable = true)
    private String linkedinUrl;

    @NotEmpty
    @Column(name=" image", nullable = false)
    private String image;

    @Column(name = "description", nullable = true)
    private String description;
    
    @NotEmpty
    @Column(name = "deleted", nullable = false)
    private boolean deleted;

   


}
