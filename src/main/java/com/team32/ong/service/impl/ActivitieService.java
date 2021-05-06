package com.team32.ong.service.impl;

import com.team32.ong.dto.ActivitiesDTO;
import com.team32.ong.model.Activities;
import com.team32.ong.repository.ActivitiesRepository;
import com.team32.ong.service.IActivitiesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ActivitieService implements IActivitiesServices {

    @Autowired
    private ActivitiesRepository activitiesRepository;

    @Override
    @Transactional
    public Activities save(ActivitiesDTO activitiesDTO, MultipartFile image) throws Exception {
        try {


        }catch (Exception e){
            throw  new Exception(e.getMessage());
        }
    }

 
}
