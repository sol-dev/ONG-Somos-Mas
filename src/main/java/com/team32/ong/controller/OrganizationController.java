package com.team32.ong.controller;

import com.team32.ong.model.OrganizationEntity;
import com.team32.ong.service.IOrganizationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/v1/organization")
public class OrganizationController {

    /*criterios:
    +Se debe realizar una petición GET hacia ruta /organization/public
    +El endpoint debe devolver solamente los campos name, image, phone y address de la organización */
    
    @Autowired
    @Qualifier("organizationService")
    private IOrganizationService organizationService;

    @GetMapping(value = "/public")
    public List<OrganizationEntity> getPublicInfo(){
        // agregar dto
        return organizationService.findAll();
    }

    @PostMapping(value = "/new", consumes = "application/json")
    public Object newOrganization(@RequestBody OrganizationEntity organization){
        System.out.println("Nueva organizacion: "+ organization.toString());
        return organizationService.save(organization);
    }

    @GetMapping(value = "/id")
    public OrganizationEntity findById(@RequestParam("id") Long id){
        return organizationService.findById(id);
    }
 
    @GetMapping(value = "/all")
    public List<OrganizationEntity> findAll(){
        return organizationService.findAll();
    }

    @PostMapping(value="/delete")
    public void softDelete(@RequestParam("id") Long id) {
        organizationService.softDelete(id);
    }



}
