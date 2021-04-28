package com.team32.ong.mapper;

import com.team32.ong.dto.MemberDTO;
import com.team32.ong.model.Member;

public class MemberMapper {

	public Member toMember(MemberDTO member) {
		return Member
				.builder()
				.memberId(member.getMemberId())
				.name(member.getName())
				.facebookUrl(member.getFacebookUrl())
				.instagramUrl(member.getInstagramUrl())
				.linkedinUrl(member.getLinkedinUrl())
				.image(member.getImage())
				.description(member.getDescription())
				.build();
	}
	
	public MemberDTO toMemberDTO(Member member) {
		return MemberDTO
				.builder()
				.memberId(member.getMemberId())
				.name(member.getName())
				.facebookUrl(member.getFacebookUrl())
				.instagramUrl(member.getInstagramUrl())
				.linkedinUrl(member.getLinkedinUrl())
				.image(member.getImage())
				.description(member.getDescription())
				.build();
	}
}
