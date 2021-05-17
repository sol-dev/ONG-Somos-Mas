package com.team32.ong.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.team32.ong.dto.OrganizationDTO;
import com.team32.ong.dto.OrganizationPublicDTO;
import com.team32.ong.exception.custom.BadRequestException;
import com.team32.ong.exception.custom.EmptyInputException;
import com.team32.ong.exception.custom.InvalidDataException;
import com.team32.ong.service.IOrganizationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javassist.NotFoundException;

@RestController
@RequestMapping("api/v1/organization")
public class OrganizationController {
    
    @Autowired
    @Qualifier("organizationService")
    private IOrganizationService organizationService;

    @GetMapping(value = "/public")
    public ResponseEntity<?> getPublicInfo(){
        List<OrganizationPublicDTO> list = organizationService.findActives();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @PostMapping(value = "/new", consumes = "application/json")
    public ResponseEntity<OrganizationDTO> newOrganization(@RequestBody OrganizationDTO organization) throws BadRequestException,EmptyInputException{
        OrganizationDTO dto = organizationService.save(organization);
        return new ResponseEntity<OrganizationDTO>(dto, HttpStatus.CREATED);
    }

    @GetMapping(value = "/id")
    public ResponseEntity<OrganizationDTO> findById( @RequestParam("id") Long id) throws NotFoundException{
        return new ResponseEntity<OrganizationDTO>(organizationService.findDtoById(id), HttpStatus.OK);
    }
 
    @GetMapping(value = "/all")
    public ResponseEntity<?> findAll(){
        List<OrganizationDTO> list = organizationService.findAll();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @PostMapping(value="/delete")
    public ResponseEntity<?> softDelete(@RequestParam("id") Long id) throws NotFoundException {
        organizationService.softDelete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value = "/update", consumes = "application/json-patch+json")  
    public ResponseEntity<OrganizationPublicDTO> update(@RequestParam("id") Long id, @RequestBody JsonPatch patch) throws NotFoundException{
        System.out.println("controller update");
        OrganizationPublicDTO organization = organizationService.update(id, patch);
        return new ResponseEntity<OrganizationPublicDTO>(organization,HttpStatus.OK);
    }

}
