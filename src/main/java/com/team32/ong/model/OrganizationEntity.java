package com.team32.ong.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import com.team32.ong.constant.ConstantExceptionMessage;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "organization")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE organization SET deleted=true WHERE id = ?")
@Where(clause = "deleted = false")
public class OrganizationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = ConstantExceptionMessage.MSG_NAME_BAD_REQUEST)
    @Column(name = "name", nullable = false)
    private String name;

    @NotEmpty(message = ConstantExceptionMessage.MSG_IMAGE_BAD_REQUEST)
    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private Integer phone;

    @Column(name = "email", nullable = false)
    @NotEmpty(message = ConstantExceptionMessage.MSG_EMAIL_BAD_REQUEST)
    @Email(message = ConstantExceptionMessage.MSG_EMAIL_INVALID)
    private String email;

    @Column (name = "welcome_text")
    private String welcomeText;

    @Column(name = "aboutUsText")
    private String aboutUsText;
    
    @OneToMany(targetEntity=Slide.class, mappedBy="organization", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Slide> slides = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "last_modified_date")
    private LocalDateTime modifiedDate;

    @Column(name = "deleted", columnDefinition = "boolean default false")
    private Boolean deleted;
    
}
