package com.team32.ong.service;

import org.springframework.stereotype.Service;

import javassist.NotFoundException;
import java.util.List;

import com.team32.ong.dto.MemberDTO;

import javassist.NotFoundException;

@Service
public interface IMemberService {

	public MemberDTO save(MemberDTO memberDTO);
	public MemberDTO updateById(MemberDTO  memberDTO, Long id) throws NotFoundException;
	public List<MemberDTO> findAll();
	
	public MemberDTO update(Long id, MemberDTO updates) throws NotFoundException;

	public void softDelete(Long id) throws NotFoundException;
}
