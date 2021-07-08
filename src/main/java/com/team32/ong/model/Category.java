package com.team32.ong.model;

import java.time.LocalDateTime;
import java.util.Set;
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
@SQLDelete(sql = "UPDATE categories SET deleted=true WHERE category_id = ?")
@Where(clause = "deleted = false")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
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

	@Column(columnDefinition = "boolean default false")
	private boolean deleted;
	@ManyToMany(mappedBy = "categories")
	private Set<News> news;

}