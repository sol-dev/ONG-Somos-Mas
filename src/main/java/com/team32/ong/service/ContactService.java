package com.team32.ong.service;

import com.team32.ong.dto.ContactDTO;
import org.springframework.stereotype.Service;

@Service
public interface ContactService {

    ContactDTO save(ContactDTO contactDTO);
}
