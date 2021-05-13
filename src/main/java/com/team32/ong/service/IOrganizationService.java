package com.team32.ong.service;

import java.util.List;

import com.team32.ong.dto.OrganizationDTO;
import com.team32.ong.dto.OrganizationPublicDTO;
import com.team32.ong.exception.custom.EmptyInputException;
import com.team32.ong.model.OrganizationEntity;

import org.springframework.data.web.JsonPath;

import javassist.NotFoundException;

public interface IOrganizationService {

    public OrganizationDTO save(OrganizationDTO dto) throws EmptyInputException;

    public OrganizationDTO findDtoById(Long id)throws NotFoundException;
  
    public OrganizationPublicDTO findPublicDtoById(Long id) throws NotFoundException;

    public List<OrganizationDTO> findAll();

    public List<OrganizationPublicDTO> findActives();

    public void softDelete(Long id) throws NotFoundException;

    public OrganizationPublicDTO update( Long id, JsonPath patch) throws NotFoundException;
    
}
