package com.team32.ong.service.impl;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team32.ong.constant.ConstantExceptionMessage;
import com.team32.ong.dto.MemberDTO;
import com.team32.ong.exception.custom.BadRequestException;
import com.team32.ong.model.Member;
import com.team32.ong.repository.MemberRepository;
import com.team32.ong.service.IMemberService;

@Service
public class MemberServiceImp implements IMemberService {

	@Autowired
	private MemberRepository repositoryMember;
	
    @Autowired
    private ModelMapper mapper;

	@Override
    @Transactional
    public MemberDTO save(MemberDTO memberDTO){
		if(memberDTO.getName().isEmpty() || memberDTO.getName().length() == 0){
            throw new BadRequestException(ConstantExceptionMessage.MSG_NAME_BAD_REQUEST);
        }
        if(memberDTO.getImage().isEmpty() || memberDTO.getImage().length() == 0){
            throw new BadRequestException(ConstantExceptionMessage.MSG_IMAGE_BAD_REQUEST);
        }
        Member member = this.dtoToModel(memberDTO);
        member.setDeleted(false);
        return modelToDTO(repositoryMember.save(member));
    }
	
	MemberDTO modelToDTO(Member member){
        return mapper.map(member, MemberDTO.class);
    }
	
    private Member dtoToModel(MemberDTO memberDTO){
        return mapper.map(memberDTO, Member.class);
    }

}
