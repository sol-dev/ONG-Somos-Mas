package com.team32.ong.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.team32.ong.constant.ConstantExceptionMessage;
import com.team32.ong.dto.OrganizationDTO;
import com.team32.ong.dto.OrganizationPublicDTO;
import com.team32.ong.exception.custom.BadRequestException;
import com.team32.ong.exception.custom.EmptyInputException;
import com.team32.ong.model.OrganizationEntity;
import com.team32.ong.repository.IOrganizationRepository;
import com.team32.ong.service.IOrganizationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
        if(dto.getImage() ==null || !isValid(dto.getImage()) ){
            throw new EmptyInputException(ConstantExceptionMessage.MSG_IMAGE_BAD_REQUEST);
        }
        if(dto.getEmail()==null || !isValid(dto.getEmail()) ){
            throw new EmptyInputException(ConstantExceptionMessage.MSG_EMAIL_BAD_REQUEST);
        }
        if(dto.getName()==null || !isValid(dto.getName()) ){
            throw new EmptyInputException(ConstantExceptionMessage.MSG_NAME_BAD_REQUEST);
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
            throw new NotFoundException(ConstantExceptionMessage.MSG_NOT_FOUND+id);
        }
        return organization.get();
    }

    public List<OrganizationPublicDTO> findAll() {
        //list of entities
        List<OrganizationEntity> lEntities= organizationRepository.findAll();
        //convert to dto list
        List<OrganizationPublicDTO> lDto = Arrays.asList(modelMapper.map(lEntities, OrganizationPublicDTO[].class));
        return lDto;
    }

    public void softDelete(Long id) throws NotFoundException {
        if(!organizationRepository.existsById(id)) {
            throw new NotFoundException(ConstantExceptionMessage.MSG_NOT_FOUND+id);
        }
        organizationRepository.deleteById(id);
    }

    public OrganizationPublicDTO update(Long id,OrganizationPublicDTO updates) throws NotFoundException{
        Optional<OrganizationEntity> organization = organizationRepository.findById(id) ;
        if(!organization.isPresent()){
            throw new NotFoundException(ConstantExceptionMessage.MSG_NOT_FOUND+id);
        }
        OrganizationEntity updatedOrganization = organization.get();
        if( isValid(updates.getFacebookUrl()) ){
            updatedOrganization.setFacebookUrl(updates.getFacebookUrl()); 
        }
        if( isValid(updates.getInstagramUrl()) ){
            updatedOrganization.setInstagramUrl(updates.getInstagramUrl());
        }
        if( isValid(updates.getLinkedinUrl()) ){
            updatedOrganization.setLinkedinUrl(updates.getLinkedinUrl());
        }
        return convertToPublicDto(organizationRepository.save(updatedOrganization));
    }

    private boolean isValid(String string){
        boolean valid = false;
        if(string.length() >0 || !string.isBlank())
            valid = true;
        return valid;
    }
}
