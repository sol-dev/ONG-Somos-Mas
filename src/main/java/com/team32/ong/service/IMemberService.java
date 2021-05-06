package com.team32.ong.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.team32.ong.dto.MemberDTO;

@Service
public interface IMemberService {

	public MemberDTO save(MemberDTO memberDTO);
	
}
