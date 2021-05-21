package com.team32.ong.service;

import com.team32.ong.dto.ContactDTO;
import java.util.List;

public interface ContactService {
	ContactDTO save(ContactDTO contactDTO);
	List<ContactDTO> getAll();
	List<ContactDTO> getAllContacts();
}
