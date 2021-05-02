package com.team32.ong.controller;

import com.team32.ong.exception.custom.InvalidDataException;
import com.team32.ong.service.impl.ActivitieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/activities")
@CrossOrigin
public class ActivitiesController {

    @Autowired
    private ActivitieService activitieService;

    /*

    @PostMapping
    public ResponseEntity<?> createActivity(@Valid @RequestBody Activities activities,
                                            BindingResult result, @RequestParam("file")
                                            MultipartFile file){

        Map<String, Object> response = new HashMap<>();
        Activities activityNew = null;

        if (result.hasErrors()){
            throw new InvalidDataException(result);
        }


            activityNew = activitieService.save(activities, file);

                response.put("message", "Actividad Guardada con exito!");
        response.put("activity", activityNew);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }
     */
}
