package com.team32.ong.model;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@SQLDelete(sql = "UPDATE news SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
@Table(name = "news")
public class News implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final int MIN_NAME_LENGTH = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id")
    private Long id;

    @Size(min = MIN_NAME_LENGTH, message = "Name must be at least " + MIN_NAME_LENGTH + " characters long")
    @NotNull(message = "El nombre no puede estar vacío.")
    @Column(name = "name", nullable = false)
    private String name;

    @NotEmpty(message = "The news must have some content.")
    @Column(name = "content", length=500, nullable = false)
    private String content;

    @Pattern(regexp="([^\\s]+(\\.(?i)(jpe?g|png))$)", message="The file must be type jpg/jpeg or png")
    @Column(name = "image")
    private String image;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "news_to_category",
        joinColumns = @JoinColumn(name = "news_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name="category_id", nullable = false)
    )
    private Set<Category> categories;

    @OneToMany(mappedBy="news", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	private Set<Comment> comments;
    
    @CreationTimestamp
    @Column(name="created_date")
     private LocalDateTime createDate;
    
    @UpdateTimestamp
    @Column(name="last_modified_date")
    private Date modifiedDate;

    @Column(name="deleted")
    private boolean deleted;

	public News(String name, String content, String image) {
		this.name = name;
		this.content = content;
		this.image = image;
	}
    
    
    
}