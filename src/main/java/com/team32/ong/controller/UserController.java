package com.team32.ong.controller;

<<<<<<< HEAD
import com.team32.ong.dto.NewuserDto;
=======
import com.team32.ong.dto.UserDto;
import com.team32.ong.exception.custom.InvalidDataException;
import com.team32.ong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
>>>>>>> b636bd9ae6365a376fd271c2031a44791e125feb
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
<<<<<<< HEAD
    public ResponseEntity<?> createUser(@Valid @RequestBody NewuserDto newuserDto, BindingResult
                                        result, MultipartFile image){
        return null;
    }

    @PutMapping
    public ResponseEntity<?> modifyUser(@Valid @RequestBody NewuserDto newuserDto, BindingResult
=======
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto nuserDto, BindingResult result){

        if (result.hasErrors()){
            throw new InvalidDataException(result);
        }else {
            return new ResponseEntity<>(userService.save(nuserDto), HttpStatus.CREATED);
        }
    }

    @PutMapping
    public ResponseEntity<?> modifyUser(@Valid @RequestBody UserDto newuserDto, BindingResult
>>>>>>> b636bd9ae6365a376fd271c2031a44791e125feb
                                        result, MultipartFile image){
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
        return null;
    }
}
