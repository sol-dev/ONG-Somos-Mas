package com.team32.ong.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "activities")
@Data @AllArgsConstructor @NoArgsConstructor @Builder
@SQLDelete(sql="UPDATE activities SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Activities extends Auditable<Date>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@NotEmpty
	@Column(name = "name", nullable = false)
	private String name;
	@NotEmpty
	@Column(name = "content")
	private String content;
	@NotEmpty
	@Column(name = "image", nullable = false)
	private String image;
	@Column(name = "deleted")
	private Boolean deleted;
}
