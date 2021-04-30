package com.team32.ong.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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
@SQLDelete(sql="UPDATE activities SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Member extends Auditable<Timestamp>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

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

    @Column(name = "deleted", nullable = false)
    private boolean deleted;
}
