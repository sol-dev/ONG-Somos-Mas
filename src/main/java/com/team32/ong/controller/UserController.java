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
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTOResponse>> getAll() {
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id){
        return null;
    }


    @PostMapping
    public ResponseEntity<UserDTOResponse> createUser(@RequestBody UserDTORequest userDTORequest) throws NotFoundException {
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
