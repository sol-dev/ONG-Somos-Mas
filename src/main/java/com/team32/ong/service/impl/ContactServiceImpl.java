package com.team32.ong.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team32.ong.dto.ContactDTO;
import com.team32.ong.model.Contact;
import com.team32.ong.repository.ContactRepository;
import com.team32.ong.service.ContactService;

@Service
@Transactional
public class ContactServiceImpl implements ContactService{
	
	@Autowired
	private ContactRepository contactRepo;

	@Override
	public List<ContactDTO> getAll() {
		List<Contact> listFound = contactRepo.findAll();
		return listFound
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
	}
	
	private ContactDTO entityToDto(Contact contact) {
		ModelMapper mapper = new ModelMapper();
		return mapper.map(contact, ContactDTO.class);
	}

}
