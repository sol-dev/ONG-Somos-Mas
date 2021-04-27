package com.team32.ong.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;



@Entity
@SQLDelete(sql="UPDATE activities SET eliminado = false WHERE id = ?")
@Where(clause = "eliminado = true")
public class Activities {

	private Long id;
	private String name;
	private String content;
	private String image;
	private Boolean eliminado;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date create_at;
	
	@PrePersist
	private void prePersist() {
		this.create_at = new Date();
		this.eliminado = true;
	}

	public Activities() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Boolean getEliminado() {
		return eliminado;
	}

	public void setEliminado(Boolean eliminado) {
		this.eliminado = eliminado;
	}

	public Date getCreate_at() {
		return create_at;
	}

	public void setCreate_at(Date create_at) {
		this.create_at = create_at;
	}

	@Override
	public String toString() {
		return "Activities [id=" + id + ", name=" + name + ", content=" + content + ", image=" + image + ", eliminado="
				+ eliminado + ", create_at=" + create_at + "]";
	}	
}
