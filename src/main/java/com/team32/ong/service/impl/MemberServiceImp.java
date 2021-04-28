package com.team32.ong.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.team32.ong.dto.MemberDTO;
import com.team32.ong.repository.MemberRepository;
import com.team32.ong.service.MemberService;

public class MemberServiceImp implements MemberService {

	@Autowired
	private MemberRepository repositoryMember;
	
	@Override
	public MemberDTO createMember(MemberDTO member) {
		return repositoryMember.save(member);
	}

}
