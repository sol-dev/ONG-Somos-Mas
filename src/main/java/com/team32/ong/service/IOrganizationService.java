package com.team32.ong.service;

import java.util.List;

import com.team32.ong.model.OrganizationEntity;

public interface IOrganizationService {

    public Object save(OrganizationEntity organization);

    public OrganizationEntity findById(final Long id);

    public void deleteMateria(Long id);

    public List<OrganizationEntity> findAll();

    public void softDelete(Long id);
}
