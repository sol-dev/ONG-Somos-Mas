package com.team32.ong.model;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@SQLDelete(sql = "UPDATE posts SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
@Table(name = "news")
public class News implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final int MIN_NAME_LENGTH = 5;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id")
    private Long id;

    @Size(min = MIN_NAME_LENGTH, message = "El nombre debe tener al menos " + MIN_NAME_LENGTH + " caracteres.")
    @NotNull(message = "El nombre no puede estar vacío.")
    @Column(name = "name", nullable = false)
    private String name;

    @NotEmpty(message = "El artículo debe tener algún contenido.")
    @Column(name = "content", length=500, nullable = false)
    private String content;

    @Pattern(regexp="([^\\s]+(\\.(?i)(jpe?g|png))$)", message="El archivo tiene que ser del tipo jpg/jpeg o png")
    @Column(name = "image")
    private String image;
    
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date", nullable = false, updatable = false)
    private Date creationDate;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "news_to_category",
        joinColumns = @JoinColumn(name = "news_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name="category_id", nullable = false)
    )
    private Set<Category> categories;

    private boolean deleted;

    public News() {
        this.categories = new HashSet<Category>();
    }
    
	/**
	 * @param id
	 * @param name
	 * @param content
	 * @param image
     * @param categories
	 */
	public News(Long id, String name, String content, String image, Set<Category> categories) {
		this.id = id;
		this.name = name;
		this.content = content;
		this.image = image;
        this.categories = categories;
	}

	public String Date(Date date){
	    String str = "Publicado el " + dateFormat.format(date); 
	    return str;
	}
}