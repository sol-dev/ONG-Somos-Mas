package com.team32.ong.service.implementation;

import java.util.List;

import com.team32.ong.model.OrganizationEntity;
import com.team32.ong.repository.IOrganizationRepository;
import com.team32.ong.service.IOrganizationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("organizationService")
public class OrganizationService implements IOrganizationService{

    @Autowired
    @Qualifier("organizationRepository")
    private IOrganizationRepository organizationRepository;

    //methods
    public Object save(OrganizationEntity organization) {
        return organizationRepository.save(organization);
    }

    public OrganizationEntity findById(Long id) {
        return organizationRepository.findById(id).get();
    }

    public void deleteMateria(Long id) {
        organizationRepository.delete(organizationRepository.findById(id).get());
    }

    public List<OrganizationEntity> findAll() {
        return organizationRepository.findAll();
    }

    public void softDelete(Long id){
        organizationRepository.softDelete(id);
    }
    
}
