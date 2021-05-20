package com.team32.ong.service;

import java.util.List;
import java.util.Map;
import com.team32.ong.dto.ImageAndOrderDto;
import com.team32.ong.dto.SlideDto;
import com.team32.ong.model.OrganizationEntity;
import org.springframework.stereotype.Service;
import javassist.NotFoundException;

@Service
public interface SlideService {

    public List<SlideDto> slideList();

    public Map<String, List<ImageAndOrderDto>> imageAndOrder();

    List<String> getOrganizationSlides(Long id) throws NotFoundException;

}
