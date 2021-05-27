package com.team32.ong.service;

import java.util.List;
import java.util.Map;
import com.team32.ong.dto.SlideDto;
import com.team32.ong.dto.SlideDtoRequest;
import org.springframework.stereotype.Service;
import javassist.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface SlideService {
	
	SlideDto findById(Long id) throws NotFoundException;

    public List<SlideDto> slideList();

    SlideDto save(SlideDtoRequest slide, MultipartFile file, Long idOrganization) throws Throwable;

    public Map<Integer, String> imageAndOrder();

}
