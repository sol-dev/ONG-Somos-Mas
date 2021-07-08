package com.team32.ong.service;

import com.team32.ong.dto.ActivitiesDTO;
import org.springframework.web.multipart.MultipartFile;

import com.team32.ong.model.Activities;

public interface IActivitiesServices {

    public ActivitiesDTO save(ActivitiesDTO activitiesDTO, MultipartFile image) throws Exception;
    ActivitiesDTO update(Long id, ActivitiesDTO activitiesDTO);
}