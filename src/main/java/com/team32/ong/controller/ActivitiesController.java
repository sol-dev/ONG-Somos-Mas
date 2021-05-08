package com.team32.ong.controller;



import com.team32.ong.dto.ActivitiesDTO;
import com.team32.ong.exception.custom.BadRequestException;
import com.team32.ong.service.impl.ActivitieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/activities")
@CrossOrigin
public class ActivitiesController {

    @Autowired
    private ActivitieService activitieService;

    private static final Logger logger = LoggerFactory.getLogger(ActivitiesController.class);

    @PostMapping
    public ResponseEntity<?> createActivity(@RequestBody ActivitiesDTO activitiesDTO,
                                            MultipartFile file) throws Exception{


        Map<String, Object> response = new HashMap<>();


       activitieService.save(activitiesDTO, file);

       response.put("message","Actividad Creada!");
       response.put("activitie", activitiesDTO);

       return new ResponseEntity(response, HttpStatus.CREATED);
    }

}
