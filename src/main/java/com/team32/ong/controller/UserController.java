package com.team32.ong.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team32.ong.dto.UserDto;
import com.team32.ong.dto.UserDtoRequestForUser;
import com.team32.ong.exception.custom.InvalidDataException;
import com.team32.ong.service.UserService;

import javassist.NotFoundException;

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
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto nuserDto, BindingResult result) throws Exception{
        if (result.hasErrors()){
            throw new InvalidDataException(result);
        }else {
            return new ResponseEntity<>(userService.save(nuserDto), HttpStatus.CREATED);
        }
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<?> modifyUserAdminOnly(@Valid @RequestBody UserDto newUserDto, BindingResult result,
                                         @PathVariable Long id) throws NotFoundException{
    	Optional<UserDto> userDtoFound =  Optional.of(userService.findById(id));
        return new ResponseEntity<>(userService.updateAdminOnly(userDtoFound, newUserDto), HttpStatus.OK);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> modifyUserAll(@Valid @RequestBody UserDtoRequestForUser userDto, BindingResult result,
                                         @PathVariable Long id) throws NotFoundException{
    	Optional<UserDto> userDtoFound =  Optional.of(userService.findById(id));
        return new ResponseEntity<>(userService.updateForUser(userDtoFound, userDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
        return null;
    }
}
