package com.team32.ong.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javassist.NotFoundException;

import com.team32.ong.dto.MemberDTO;

@Service
public interface IMemberService {

	public MemberDTO save(MemberDTO memberDTO);
	
	public MemberDTO update(Long id, MemberDTO updates) throws NotFoundException;
}
