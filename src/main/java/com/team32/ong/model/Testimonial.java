package com.team32.ong.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table (name = "Testimonials")
@SQLDelete(sql = "UPDATE categories SET deleted=true WHERE id = ?")
@Where(clause = "deleted = false")
public class Testimonial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name may not be empty")
    @Column(name="name", nullable = false)
    private String name;

    @NotEmpty(message = "Image may not be empty")
    @Column(name="image")
    private String image;

    @Column(name="content")
    private String content;

    @Column(name="deleted")
    private Boolean deleted;

    @Column(name="registDate")
    private LocalDateTime registDate;

}
