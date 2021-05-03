package com.team32.ong.model;

import java.sql.Date;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="member")
public class Member{
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
    
    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    @NotEmpty
    @Column(name = "deleted", nullable = false)
    private boolean deleted;

   


}
