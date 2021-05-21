package com.team32.ong.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javassist.NotFoundException;
import java.util.List;

import com.team32.ong.dto.MemberDTO;

@Service
public interface IMemberService {

	public MemberDTO save(MemberDTO memberDTO);

	public List<MemberDTO> findAll();
	
	public MemberDTO update(Long id, MemberDTO updates) throws NotFoundException;
}
