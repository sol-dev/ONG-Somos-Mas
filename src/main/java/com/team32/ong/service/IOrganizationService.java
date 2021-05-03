package com.team32.ong.service;

import java.util.List;

import com.team32.ong.dto.OrganizationDTO;

public interface IOrganizationService {

    public OrganizationDTO save(OrganizationDTO dto);

    public OrganizationDTO findById(final Long id);

    public List<OrganizationDTO> findAll();

    public List<OrganizationDTO> findActives();

    public void deleteMateria(Long id);

    public void softDelete(Long id);
}
