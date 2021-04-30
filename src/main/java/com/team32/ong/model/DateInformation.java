package com.team32.ong.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data @AllArgsConstructor @NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class DateInformation {

    @CreatedDate
    @Column(name = "created_date")
    private Timestamp createDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Timestamp lastModifiedDate;

}
