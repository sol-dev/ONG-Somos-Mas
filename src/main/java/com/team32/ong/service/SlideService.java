package com.team32.ong.service;

import java.util.List;
import com.team32.ong.dto.SlideDto;
import com.team32.ong.dto.SlideDtoRequest;
import org.springframework.stereotype.Service;
import java.util.TreeMap;
import javassist.NotFoundException;

import org.springframework.web.multipart.MultipartFile;

@Service
public interface SlideService {
	
	SlideDto findById(Long id) throws NotFoundException;

    SlideDto save(SlideDtoRequest slide, Long idOrganization) throws Throwable;

    SlideDto updateImage(MultipartFile multipartFile, Long id) throws Throwable;

    TreeMap<String, TreeMap<Integer, String>> imageAndOrderByOrganization();

    List<String> getOrganizationSlides(Long id) throws NotFoundException;
    public SlideDto update(Long id, SlideDtoRequest slideDtoRequest) throws NotFoundException;

}
