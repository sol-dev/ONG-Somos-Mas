package com.team32.ong.controller;

import com.team32.ong.dto.UserDTORequest;
import com.team32.ong.dto.UserDTOResponse;
import com.team32.ong.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
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

import com.team32.ong.dto.UserDtoRequestForAdmin;
import com.team32.ong.exception.custom.BadRequestException;

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
    public ResponseEntity<UserDTOResponse> createUser(@RequestBody UserDTORequest userDTORequest) throws NotFoundException, BadRequestException, IOException {
        return new ResponseEntity<>(userService.save(userDTORequest), HttpStatus.CREATED);
    }
    
    @PutMapping("/admin/update/{id}")
    public ResponseEntity<?> modifyUserAdminOnly(@RequestBody UserDtoRequestForAdmin newUserDto,
                                         @PathVariable Long id) throws NotFoundException{
        return new ResponseEntity<>(userService.updateAdminOnly(id, newUserDto), HttpStatus.OK);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> modifyUserAll(@RequestBody UserDTORequest userDto,
                                         @PathVariable Long id) throws NotFoundException{
        return new ResponseEntity<>(userService.updateForUser(id, userDto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) throws NotFoundException{
    	return new ResponseEntity<>(userService.delete(id),HttpStatus.OK);
    }
}
