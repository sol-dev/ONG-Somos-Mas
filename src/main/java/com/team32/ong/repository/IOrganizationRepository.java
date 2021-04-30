package com.team32.ong.repository;

import java.io.Serializable;
import com.team32.ong.model.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("organizationRepository")
public interface IOrganizationRepository extends JpaRepository<OrganizationEntity, Serializable> {

    @Modifying
    @Transactional
    @Query("UPDATE OrganizationEntity o SET o.deleted = true WHERE o.id = :_id")
    public abstract void softDelete(@Param("_id") Long id);

}
