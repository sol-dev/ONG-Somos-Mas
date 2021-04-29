package com.team32.ong.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.team32.ong.dto.MemberDTO;
import com.team32.ong.model.Member;

@Component
public class MemberMapper {

	public Member toMember(MemberDTO memberDTO) {
		ModelMapper mapper = new ModelMapper();
		Member member = mapper.map(memberDTO, Member.class);
		return member;
	}
	
	public MemberDTO toMemberDTO(Member member) {
		ModelMapper mapper = new ModelMapper();
		MemberDTO memberDTO = mapper.map(member, MemberDTO.class);
		return null;
	}
}
