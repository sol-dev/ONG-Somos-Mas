package com.team32.ong.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.team32.ong.dto.MemberDTO;
import com.team32.ong.mapper.MemberMapper;
import com.team32.ong.model.Member;
import com.team32.ong.repository.MemberRepository;
import com.team32.ong.service.MemberService;

public class MemberServiceImp implements MemberService {

	@Autowired
	private MemberRepository repositoryMember;
	
	@Autowired
	private MemberMapper mapper;
	
	@Override
	public MemberDTO createMember(MemberDTO memberDTO) {
		Member member = repositoryMember.save(mapper.toMember(memberDTO));
		return mapper.toMemberDTO(member);
	}

}
