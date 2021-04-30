package com.team32.ong.service;

import org.springframework.stereotype.Service;

import com.team32.ong.model.Member;

@Service
public interface MemberService {

	public Member createMember(Member member);
	
}
