package com.team32.ong.model;

import lombok.Data;
import org.springframework.data.jpa.repository.Temporal;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table
@Data
public class OrganizationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private Integer phone;

    @Column(name = "email")
    private String email;

    @Column (name = "welcome_text")
    private String welcomeText;

    @Column(name = "aboutUsText")
    private String aboutUsText;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "date")
    private Timestamp date;
}
