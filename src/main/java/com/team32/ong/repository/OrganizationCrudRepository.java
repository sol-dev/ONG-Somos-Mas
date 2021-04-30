package com.team32.ong.repository;

import com.team32.ong.model.OrganizationEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrganizationCrudRepository extends CrudRepository<OrganizationEntity, Long> {
}
