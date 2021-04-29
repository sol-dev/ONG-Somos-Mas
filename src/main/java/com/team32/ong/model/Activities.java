package com.team32.ong.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "activities")
@SQLDelete(sql="UPDATE activities SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Activities {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String name;
	@NotEmpty
	@Size(min = 15, max = 100)
	private String content;
	private String image;
	private Boolean deleted;
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime create_at;	
}
