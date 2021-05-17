package com.team32.ong.service;

import com.team32.ong.dto.CategoryDTO;

import javassist.NotFoundException;

import org.springframework.stereotype.Service;

import javassist.NotFoundException;

@Service
public interface CategoryService {

    CategoryDTO save(CategoryDTO categoryDTO);
    void delete(Long id) throws NotFoundException;
    public CategoryDTO findById(final Long id) throws NotFoundException;
}
