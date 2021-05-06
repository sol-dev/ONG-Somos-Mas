package com.team32.ong.controller;



import com.team32.ong.dto.ActivitiesDTO;
import com.team32.ong.exception.custom.BadRequestException;
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



    @PostMapping
    public ResponseEntity<?> createActivity(@Valid @RequestBody ActivitiesDTO activitiesDTO,
                                            BindingResult result,
                                            MultipartFile file) throws Exception{

        Map<String, Object> response = new HashMap<>();


       if (activitiesDTO == null){
           throw new BadRequestException("No se Recibe Actividad");
       }

       activitieService.save(activitiesDTO, file);

       response.put("message","Actividad Creada!");
       response.put("actiivitie", activitiesDTO);

       return new ResponseEntity(response, HttpStatus.CREATED);
    }

}
