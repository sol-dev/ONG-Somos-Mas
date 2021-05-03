package com.team32.ong.controller;

import com.team32.ong.dto.OrganizationDTO;
import com.team32.ong.service.IOrganizationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/v1/organization")
public class OrganizationController {
    
    @Autowired
    @Qualifier("organizationService")
    private IOrganizationService organizationService;

    @GetMapping(value = "/public")
    public List<OrganizationDTO> getPublicInfo(){
        return organizationService.findActives();
    }

    @PostMapping(value = "/new", consumes = "application/json")
    public Object newOrganization(@RequestBody OrganizationDTO organization){
        System.out.println("Nueva organizacion: "+ organization.toString());
        return organizationService.save(organization);
    }

    @GetMapping(value = "/id")
    public Object findById(@RequestParam("id") Long id){
        return organizationService.findById(id);
    }
 
    @GetMapping(value = "/all")
    public List<OrganizationDTO> findAll(){
        return organizationService.findAll();
    }

    @PostMapping(value="/delete")
    public void softDelete(@RequestParam("id") Long id) {
        organizationService.softDelete(id);
    }



}
