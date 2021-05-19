package com.team32.ong.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Contacts")
@SQLDelete(sql = "UPDATE contacts SET deleted=true WHERE id = ?")
@Where(clause = "deleted = false")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty()
    @Column(name="name", nullable = false)
    private String name;

    @NotEmpty()
    @Email
    @Column(name="email", nullable = false)
    private String email;

    @NotEmpty()
    @Column(name="message", nullable = false)
    private String message;

    @Column(name="phone", nullable = false)
    private Long phone;

    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "deletedAt")
    private LocalDateTime deletedAt;

    @Column(name="deleted")
    private Boolean deleted;
}
