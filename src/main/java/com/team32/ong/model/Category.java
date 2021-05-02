package com.team32.ong.model;


<<<<<<< HEAD

import java.time.LocalDateTime;
=======
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

>>>>>>> f721c08a93e7c0a7ce6d4825afb69764c1bd398e
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE categories SET deleted=true WHERE id = ?")
@Where(clause = "deleted = false")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	@NotEmpty
	private String name;
	private String description;
	private String image;

	@CreationTimestamp
	@Column(name = "created_date")
	private LocalDateTime createDate;

	@UpdateTimestamp
	@Column(name = "last_modified_date")
	private LocalDateTime modifiedDate;

	private boolean deleted;
	
	@ManyToMany(mappedBy = "categories")
	private Set<News> news;

}