package com.team32.ong.controller;

import com.team32.ong.dto.UserDTORequest;
import com.team32.ong.dto.UserDTOResponse;
import com.team32.ong.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

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
    public ResponseEntity<UserDTOResponse> createUser(@Valid @RequestBody UserDTORequest userDTORequest,
                                                   BindingResult result) throws NotFoundException{
            return new ResponseEntity<>(userService.save(userDTORequest), HttpStatus.CREATED);
        }

    @PutMapping
    public ResponseEntity<?> modifyUser(@Valid @RequestBody UserDTORequest newuserDTORequest, BindingResult
                                        result, MultipartFile image){
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
        return null;
    }
}
