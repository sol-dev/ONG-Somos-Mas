package com.team32.ong.service;

import org.springframework.stereotype.Service;

import com.team32.ong.dto.MemberDTO;

@Service
public interface MemberService {

	public MemberDTO createMember(MemberDTO member);
	
}
