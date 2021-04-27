package com.team32.ong.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table
@Data
public class OrganizationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private Integer phone;

    @Column(name = "email", nullable = false)
    private String email;

    @Column (name = "welcome_text")
    private String welcomeText;

    @Column(name = "aboutUsText")
    private String aboutUsText;

    @Column(name = "deleted")
    private Boolean deleted;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp date;

}
