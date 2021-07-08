package com.team32.ong.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="member")
@SQLDelete(sql="UPDATE member SET deleted = true WHERE member_id = ?")
@Where(clause = "deleted = false")
public class Member{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @NotEmpty
    @Pattern(regexp = "[^0-9]*", message = "El campo name no debe contener numeros!!")
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

    @Pattern(regexp = "[^0-9]*", message = "El campo name no debe contener numeros!!")
    @Column(name = "description", nullable = true)
    private String description;
    
    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;
}
