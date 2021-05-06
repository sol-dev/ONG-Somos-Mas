package com.team32.ong.controller;

import com.team32.ong.dto.UserRequest;
import com.team32.ong.dto.UserResponse;
import com.team32.ong.exception.custom.InvalidDataException;
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
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest,
                                                   BindingResult result) throws NotFoundException {

        if (result.hasErrors()){
            throw new InvalidDataException(result);
        }else {
            return new ResponseEntity<>(userService.save(userRequest), HttpStatus.CREATED);
        }
    }

    @PutMapping
    public ResponseEntity<?> modifyUser(@Valid @RequestBody UserRequest newuserRequest, BindingResult
                                        result, MultipartFile image){
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
        return null;
    }
}
