package com.team32.ong.service;

import org.springframework.web.multipart.MultipartFile;

public interface IActivitiesServices {

    public Activities save(Activities activities, MultipartFile image) throws Exception;
}
