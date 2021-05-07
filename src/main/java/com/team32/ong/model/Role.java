package com.team32.ong.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor

public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private String name;
    @Column(length = 50)
    private String description;
    @CreationTimestamp
    private LocalDateTime created_time;
    @UpdateTimestamp
    private LocalDateTime update_time;


    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
