package com.team32.ong.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "facebookUrl", nullable = true)
    private String facebookUrl;

    @Column(name = "instagramUrl", nullable = true)
    private String instagramUrl;

    @Column(name = "linkedinUrl", nullable = true)
    private String linkedinUrl;

    @Column(name=" image", nullable = false)
    private String image;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "createdAt", nullable = false)
    private Date createdAt;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    public Member(){}

    public Member(int memberId, String name, String facebookUrl, String instagramUrl, String linkedinUrl, String image,
            String description, Date createdAt, boolean deleted) {
        this.memberId = memberId;
        this.name = name;
        this.facebookUrl = facebookUrl;
        this.instagramUrl = instagramUrl;
        this.linkedinUrl = linkedinUrl;
        this.image = image;
        this.description = description;
        this.createdAt = createdAt;
        this.deleted = deleted;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public String getInstagramUrl() {
        return instagramUrl;
    }

    public void setInstagramUrl(String instagramUrl) {
        this.instagramUrl = instagramUrl;
    }

    public String getLinkedinUrl() {
        return linkedinUrl;
    }

    public void setLinkedinUrl(String linkedinUrl) {
        this.linkedinUrl = linkedinUrl;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }


}
