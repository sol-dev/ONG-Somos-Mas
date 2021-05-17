package com.team32.ong.repository;

import java.io.Serializable;

import com.team32.ong.model.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("organizationRepository")
public interface IOrganizationRepository extends JpaRepository<OrganizationEntity, Serializable> {
   
}
