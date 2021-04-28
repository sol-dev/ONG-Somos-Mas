package com.team32.ong.controller;

import com.team32.ong.service.impl.ActivitieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/activities/")
@CrossOrigin
public class ActivitiesController {

    @Autowired
    private ActivitieService activitieService;

    @PostMapping
    public ResponseEntity<?> createActivity(@Valid @RequestBody Activities activities,
                                            BindingResult result, @RequestParam("file")
                                            MultipartFile file){

        Map<String, Object> response = new HashMap<>();
        Activities activityNew = null;

        if (result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El Campo " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }

        try {
            activityNew = activitieService.save(activities, file);
        }catch (Exception e){
            response.put("message", "Error al Guardar Actividad");
            response.put("error", e.getMessage());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
        response.put("message", "Actividad Guardada con exito!");
        response.put("activity", activityNew);
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
