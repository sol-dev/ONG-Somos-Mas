package com.team32.ong.service.impl;

import com.team32.ong.dto.ContactDTO;
import com.team32.ong.model.Contact;
import com.team32.ong.repository.ContactRepository;
import com.team32.ong.service.ContactService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactRepository contactRepository;

    @Override
    public List<ContactDTO> getAllContacts() {
        List<Contact> contactList = contactRepository.findAll();

        return mapContactListToContacDTOList(contactList, ContactDTO.class);
    }

    private List<ContactDTO> mapContactListToContacDTOList(List<Contact> contactList, Class<ContactDTO> contactDTO) {
        ModelMapper mapper = new ModelMapper();
        return contactList
                .stream()
                .map(element -> mapper.map(element, contactDTO))
                .collect(Collectors.toList());
    }
}
