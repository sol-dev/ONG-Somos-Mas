package com.team32.ong.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import com.team32.ong.model.Role;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "UPDATE posts SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(length = 50)
    private String firstName;
    @NotNull
    @Column(length = 50)
    private String lastName;
    @NotNull
    @Email
    @Column(unique = true, length = 80)
    private String email;
    @NotNull
    @Column(length = 60)
    private String password;
    private String photo;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "roleId", referencedColumnName = "id")
    private Role role;
    private boolean deleted;
    @CreationTimestamp
    private LocalDateTime created_time;
    @UpdateTimestamp
    private LocalDateTime updated_time;
    
    @OneToMany(mappedBy="user", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	private Set<Comment> comments;


    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;

    }
}
