package com.team32.ong.controller;

import com.team32.ong.dto.NewuserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin
public class UserController {

    @GetMapping
    public ResponseEntity<?> vievAll(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "5") int size,
                                     @RequestParam(defaultValue = "id") String order,
                                     @RequestParam(defaultValue = "true") boolean asc) {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id){
        return null;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody NewuserDto newuserDto, BindingResult
                                        result, MultipartFile image){
        return null;
    }

    @PutMapping
    public ResponseEntity<?> modifyUser(@Valid @RequestBody NewuserDto newuserDto, BindingResult
                                        result, MultipartFile image){
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
        return null;
    }
}
