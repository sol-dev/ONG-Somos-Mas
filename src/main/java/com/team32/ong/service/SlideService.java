package com.team32.ong.service;

import java.util.List;
import java.util.TreeMap;
import com.team32.ong.dto.SlideDto;
import org.springframework.stereotype.Service;
import javassist.NotFoundException;

@Service
public interface SlideService {

    public List<SlideDto> slideList();

    public TreeMap<String, TreeMap<Integer, String>> imageAndOrderByOrganization();

    List<String> getOrganizationSlides(Long id) throws NotFoundException;

}
