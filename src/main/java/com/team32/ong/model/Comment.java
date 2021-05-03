package com.team32.ong.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import lombok.Data;

@Data
@Entity
@Table (name = "comments")
@SQLDelete(sql = "UPDATE comments SET deleted=true WHERE id = ?")
@Where(clause = "deleted = false")
public class Comment implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="body")
    private String body;

    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "last_modified_date")
    private LocalDateTime modifiedDate;

    @Column(name="deleted")
    private Boolean deleted;

    // ManyToOne to table user
    @ManyToOne()
    @JoinColumn(name="user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;
    
    // ManyToOne to table news
    @ManyToOne()
    @JoinColumn(name="news_id", referencedColumnName = "news_id", insertable = false, updatable = false)
    private News news;
       
}


/*
Description
COMO usuario 
QUIERO agregar un nuevo comentario a un post
PARA opinar acerca de dicho post.

Criterios de aceptación:
Se agregará la posibilidad de agregar comentarios a un Post. Los campos de un comentario serán user_id, body y news_id
*/