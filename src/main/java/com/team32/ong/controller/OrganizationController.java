package com.team32.ong.controller;

import com.team32.ong.dto.OrganizationDTO;
import com.team32.ong.exception.custom.InvalidDataException;
import com.team32.ong.service.IOrganizationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;

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
    public ResponseEntity<OrganizationDTO> newOrganization(@Valid @RequestBody OrganizationDTO organization, BindingResult result){
        Map<String, Object> response = new HashMap<>();
        OrganizationDTO newDto = null;
        newDto = organizationService.save(organization);
        response.put("message", "Organizacion Guardada con exito!");
        response.put("organization", newDto);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @GetMapping(value = "/id")
    public ResponseEntity<OrganizationDTO> findById( @RequestParam("id") Long id){
       return new ResponseEntity<OrganizationDTO>(organizationService.findById(id), HttpStatus.FOUND);
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
