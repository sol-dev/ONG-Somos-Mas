package com.team32.ong.service;

import java.util.List;

import com.team32.ong.dto.OrganizationDTO;
import com.team32.ong.dto.OrganizationPublicDTO;
import com.team32.ong.exception.custom.EmptyInputException;

import javassist.NotFoundException;

public interface IOrganizationService {

    public OrganizationDTO save(OrganizationDTO dto) throws EmptyInputException;

    public OrganizationDTO findDtoById(Long id)throws NotFoundException;
  
    public OrganizationPublicDTO findPublicDtoById(Long id) throws NotFoundException;

    public List<OrganizationPublicDTO> findAll();

    public void softDelete(Long id) throws NotFoundException;

    public OrganizationPublicDTO update(Long id,OrganizationPublicDTO updates) throws NotFoundException;

}
