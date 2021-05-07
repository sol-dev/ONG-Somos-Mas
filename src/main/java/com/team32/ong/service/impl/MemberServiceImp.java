package com.team32.ong.service.impl;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team32.ong.dto.MemberDTO;
import com.team32.ong.exception.custom.EmptyInputException;
import com.team32.ong.model.Member;
import com.team32.ong.repository.MemberRepository;
import com.team32.ong.service.IMemberService;

@Service
public class MemberServiceImp implements IMemberService {

	@Autowired
	private MemberRepository repositoryMember;
	
	@Override
    @Transactional
    public MemberDTO save(MemberDTO memberDTO){
		if(memberDTO.getName().isEmpty() || memberDTO.getName().length() == 0){
            throw new EmptyInputException("Debe completar el campo nombre");
        }
        if(memberDTO.getImage().isEmpty() || memberDTO.getImage().length() == 0){
            throw new EmptyInputException("Debe completar el campo imagen");
        }
        Member member = this.dtoToModel(memberDTO);
        member.setDeleted(false);
        return modelToDTO(repositoryMember.save(member));
    }
	
	MemberDTO modelToDTO(Member member){
        ModelMapper mapper = new ModelMapper();
        MemberDTO map = mapper.map(member, MemberDTO.class);
        return map;
    }
	
    private Member dtoToModel(MemberDTO memberDTO){
        ModelMapper mapper = new ModelMapper();
        Member map = mapper.map(memberDTO, Member.class);
        return map;

    }

}
