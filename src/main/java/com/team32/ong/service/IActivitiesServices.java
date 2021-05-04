package com.team32.ong.service;

import org.springframework.web.multipart.MultipartFile;

import com.team32.ong.model.Activities;

public interface IActivitiesServices {

    public Activities save(Activities activities, MultipartFile image) throws Exception;
}
