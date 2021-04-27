package com.team32.ong.controller;

import com.team32.ong.dto.Organization;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

    @GetMapping()
    public ResponseEntity<List<Organization>> getAll(){
        return null;
    }

    @GetMapping("/{idOrganization}")
    public ResponseEntity<Organization> getById(@PathVariable("idOrganization") Long idOrganization){
        return null;
    }

    @PostMapping("/create")
    public ResponseEntity<Organization> save(@RequestBody Organization organization){
        return null;
    }



    @DeleteMapping("/{idOrganization}")
    public ResponseEntity delete(@PathVariable("idOrganization") Long idOrganization){
        return null;
    }
}
