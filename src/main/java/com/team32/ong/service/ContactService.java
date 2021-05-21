package com.team32.ong.service;

import java.util.List;

import com.team32.ong.dto.ContactDTO;

public interface ContactService {

	ContactDTO save(ContactDTO contactDTO);
	List<ContactDTO> getAll();
}
