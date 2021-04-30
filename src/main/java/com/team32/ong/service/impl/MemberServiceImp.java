package com.team32.ong.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team32.ong.model.Member;
import com.team32.ong.repository.MemberRepository;
import com.team32.ong.service.MemberService;

@Service
public class MemberServiceImp implements MemberService {

	@Autowired
	private MemberRepository repositoryMember;
	
	@Override
	public Member createMember(Member member) {
		member.setDeleted(false);
		return repositoryMember.save(member);
	}

}
