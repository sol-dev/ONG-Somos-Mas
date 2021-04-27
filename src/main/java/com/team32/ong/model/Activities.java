package com.team32.ong.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SQLDelete(sql="UPDATE activities SET delete = true WHERE id = ?")
@Where(clause = "delete = false")
public class Activities {

	private Long id;
	private String name;
	private String content;
	private String image;
	private Boolean delete;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date create_at;
	
	@PrePersist
	private void prePersist() {
		this.create_at = new Date();
		this.delete = false;
	}	
}
