package com.team32.ong.service.impl;

import java.util.Arrays;
import java.util.List;

import com.team32.ong.dto.OrganizationDTO;
import com.team32.ong.model.OrganizationEntity;
import com.team32.ong.repository.IOrganizationRepository;
import com.team32.ong.service.IOrganizationService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("organizationService")
public class OrganizationService implements IOrganizationService{

    @Autowired
    @Qualifier("organizationRepository")
    private IOrganizationRepository organizationRepository;

    //model mapper
    @Autowired
    private ModelMapper modelMapper;

    private OrganizationDTO convertToDto(OrganizationEntity entity){
        OrganizationDTO dto = modelMapper.map(entity, OrganizationDTO.class);
        return dto;
    }

    protected OrganizationEntity convertToEntity(OrganizationDTO dto){
        return modelMapper.map(dto, OrganizationEntity.class);
    }
    
    //controller methods
    public OrganizationDTO save(OrganizationDTO dto) {
        OrganizationEntity entity = convertToEntity(dto);
        entity.setDeleted(false);
        return convertToDto(organizationRepository.save(entity));
    }

    public OrganizationDTO findById(Long id) {
        //throws IllegalArgumentException 
        OrganizationEntity entity = organizationRepository.findById(id).get();
        return convertToDto(entity);
    }
    /*public OrganizationDTO findById(Long id) {
        OrganizationEntity entity = organizationRepository.findById(id).get();
        return convertToDto(entity);
    } */

    public List<OrganizationDTO> findAll() {
        //list of entities
        List<OrganizationEntity> lEntities= organizationRepository.findAll();
        //convert to dto list
        List<OrganizationDTO> lDto = Arrays.asList(modelMapper.map(lEntities, OrganizationDTO[].class));
        return lDto;
    }

    public List<OrganizationDTO> findActives() {
        List<OrganizationEntity> lEntities= organizationRepository.findActives();
        List<OrganizationDTO> lDto = Arrays.asList(modelMapper.map(lEntities, OrganizationDTO[].class));
        return lDto;
    }

    public void deleteMateria(Long id) {
        organizationRepository.delete(organizationRepository.findById(id).get());
    }

    public void softDelete(Long id){
        organizationRepository.softDelete(id);
    }

}
