package com.team32.ong.service.impl;

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
    public Activities save(Activities activities, MultipartFile image) throws Exception {
        try {
            if (!image.isEmpty()){
                String uniqueFilename = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
                Path rootPath = Paths.get("upload").resolve(uniqueFilename);
                Path rootAbsolutepath = rootPath.toAbsolutePath();
                Files.copy(image.getInputStream(), rootAbsolutepath);
                Activities.setImange(uniqueFilename);
            }else {
                Activities.setImange("");
            }

            return activitiesRepository.save(activities);

        }catch (Exception e){
            throw  new Exception(e.getMessage());
        }
    }
}
