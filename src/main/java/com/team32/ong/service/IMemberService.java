package com.team32.ong.service;

import org.springframework.stereotype.Service;

import com.team32.ong.dto.MemberDTO;

import javassist.NotFoundException;

@Service
public interface IMemberService {

	public MemberDTO save(MemberDTO memberDTO);
	public MemberDTO updateById(MemberDTO  memberDTO, Long id) throws NotFoundException;
	
}
