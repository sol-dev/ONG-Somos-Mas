package com.team32.ong.controller;

import com.team32.ong.dto.RoleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/role")
@CrossOrigin
public class RoleController {

    @PostMapping
    public ResponseEntity<?> createRole(@Valid @RequestBody RoleDto roleDto, BindingResult result) {
        return null;
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getRole(@PathVariable("name") String name) {
        return null;
    }

    @GetMapping
    public ResponseEntity<?> viewRoles() {
        return null;
    }

    @PutMapping
    public ResponseEntity<?> modifyRole(@Valid @RequestBody RoleDto roleDto, BindingResult result) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable("id") Long id) {
        return null;
    }
}
