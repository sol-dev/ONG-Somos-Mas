package com.team32.ong.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;

import com.team32.ong.dto.MemberDTO;

@Service
public interface IMemberService {

	public MemberDTO save(MemberDTO memberDTO);
	
	public MemberDTO updateById(MemberDTO  memberDTO, Long id) throws NotFoundException;
	
	public String getMembers(Pageable page) throws NotFoundException;

	public void softDelete(Long id) throws NotFoundException;
}
