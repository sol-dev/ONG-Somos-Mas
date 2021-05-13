package com.team32.ong.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import com.team32.ong.constant.*;
import com.team32.ong.dto.OrganizationDTO;
import com.team32.ong.dto.OrganizationPublicDTO;
import com.team32.ong.exception.custom.EmptyInputException;
import com.team32.ong.model.OrganizationEntity;
import com.team32.ong.repository.IOrganizationRepository;
import com.team32.ong.service.IOrganizationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.web.JsonPath;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;

@Service("organizationService")
public class OrganizationService implements IOrganizationService{

    @Autowired
    @Qualifier("organizationRepository")
    private IOrganizationRepository organizationRepository;

    //model mapper
    @Autowired
    private ModelMapper modelMapper;
/*
    private OrganizationDTO convertToDto(@Valid OrganizationEntity entity){
        OrganizationDTO dto = modelMapper.map(entity, OrganizationDTO.class);
        return dto;
    }*/
    //OrganizationDTO
    private OrganizationDTO convertToDto(@Valid OrganizationEntity entity){
        return modelMapper.map(entity, OrganizationDTO.class);
    }

    private OrganizationEntity convertDtoToEntity(OrganizationDTO dto){
        return modelMapper.map(dto, OrganizationEntity.class);
    }
    //OrganizationPublicDTO
    private OrganizationPublicDTO convertToPublicDto(@Valid OrganizationEntity entity){
        return modelMapper.map(entity, OrganizationPublicDTO.class);
    }

    private OrganizationEntity convertPublicDtoToEntity(OrganizationPublicDTO dto){
        return modelMapper.map(dto, OrganizationEntity.class);
    }
    
    //controller methods
    public OrganizationDTO save(OrganizationDTO dto) throws EmptyInputException {
        if(dto.getImage()==null || dto.getImage().isBlank()){
            throw new EmptyInputException(ConstantMessage.MSG_IMAGE_BAD_REQUEST);
        }
        if(dto.getEmail()==null || dto.getEmail().isBlank()){
            throw new EmptyInputException(ConstantMessage.MSG_EMAIL_BAD_REQUEST);
        }
        if(dto.getName()==null || dto.getName().isBlank()){
            throw new EmptyInputException(ConstantMessage.MSG_NAME_BAD_REQUEST);
        }
        OrganizationEntity entity = convertDtoToEntity(dto);
        entity.setDeleted(false);
        return convertToDto(organizationRepository.save(entity));
    }

    //controller findById
    public OrganizationDTO findDtoById(Long id)throws NotFoundException{
        return convertToDto(findById(id));
    }

    public OrganizationPublicDTO findPublicDtoById(Long id) throws NotFoundException{
        return convertToPublicDto(findById(id));
    }
    //intern findById
    protected OrganizationEntity findById(Long id) throws NotFoundException{
        Optional<OrganizationEntity> organization = organizationRepository.findById(id) ;
        if(!organization.isPresent()){
            throw new NotFoundException(ConstantMessage.MSG_NOT_FOUND+id);
        }
        return organization.get();
    }

    public List<OrganizationDTO> findAll() {
        //list of entities
        List<OrganizationEntity> lEntities= organizationRepository.findAll();
        //convert to dto list
        List<OrganizationDTO> lDto = Arrays.asList(modelMapper.map(lEntities, OrganizationDTO[].class));
        return lDto;
    }

    public List<OrganizationPublicDTO> findActives() {
        List<OrganizationEntity> lEntities= organizationRepository.findActives();
        List<OrganizationPublicDTO> lDto = Arrays.asList(modelMapper.map(lEntities, OrganizationPublicDTO[].class));
        return lDto;
    }

    public void softDelete(Long id) throws NotFoundException {
        if(!organizationRepository.existsById(id)) {
            throw new NotFoundException(ConstantMessage.MSG_NOT_FOUND+id);
        }
        organizationRepository.softDelete(id);
    }

    public OrganizationPublicDTO update(Long id, JsonPath patch) throws NotFoundException {
        return null;
    }

}
