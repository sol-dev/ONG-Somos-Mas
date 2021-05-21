package com.team32.ong.service;

import java.util.List;
import java.util.Map;

import com.team32.ong.dto.SlideDto;

import org.springframework.stereotype.Service;

@Service
public interface SlideService {

    public List<SlideDto> slideList();

    public Map<Integer, String> imageAndOrder();

}
