package com.team32.ong.service.impl;


import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javassist.NotFoundException;

import com.team32.ong.component.PaginationComponent;
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
    
    @Autowired
    private PaginationComponent paginationComponent;

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
	
	@Override
    public MemberDTO updateById(MemberDTO memberDtoUpdate, Long id) throws NotFoundException {

        if(!repositoryMember.existsById(id)){
            throw new NotFoundException( ConstantExceptionMessage.MSG_NOT_FOUND + id);
        }
        
        if(memberDtoUpdate.getName().isEmpty() || memberDtoUpdate.getName().length() == 0){
            throw new BadRequestException(ConstantExceptionMessage.MSG_NAME_BAD_REQUEST);
        }

        if(memberDtoUpdate.getImage().isEmpty() || memberDtoUpdate.getImage().length() == 0){
            throw new BadRequestException(ConstantExceptionMessage.MSG_IMAGE_BAD_REQUEST);
        }
        
        Optional<Member> members = repositoryMember.findById(id);
        
        Member memberDToUpdate = members.get();
        memberDToUpdate.setDescription(memberDtoUpdate.getDescription());
        memberDToUpdate.setFacebookUrl(memberDtoUpdate.getFacebookUrl());
        memberDToUpdate.setImage(memberDtoUpdate.getImage());
        memberDToUpdate.setInstagramUrl(memberDtoUpdate.getInstagramUrl());
        memberDToUpdate.setLinkedinUrl(memberDtoUpdate.getLinkedinUrl());
        memberDToUpdate.setName(memberDtoUpdate.getName());

        repositoryMember.save(memberDToUpdate);

        return modelToDTO(memberDToUpdate);
    }
	
	MemberDTO modelToDTO(Member member){
        return mapper.map(member, MemberDTO.class);
    }
	
    private Member dtoToModel(MemberDTO memberDTO){
        return mapper.map(memberDTO, Member.class);
    }

    
    @Override
    public String getMembers(Pageable page) throws NotFoundException {
    	Page<Member> members = repositoryMember.findAll(page);
    	if(members.getTotalPages() <= page.getPageNumber()){
            throw new NotFoundException(ConstantExceptionMessage.MSG_PAGE_NOT_FOUND);
        }
        return paginationComponent.changePaginationResponse(members.map(this::modelToDTO));
    }

    @Override
    public void softDelete(Long id) throws NotFoundException {
        if(!repositoryMember.existsById(id))
            throw new NotFoundException(ConstantExceptionMessage.MSG_NOT_FOUND+id);
        repositoryMember.deleteById(id);     
    }

}
