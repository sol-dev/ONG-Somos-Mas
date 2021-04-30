package com.team32.ong.service.impl;


import com.team32.ong.model.Category;
import com.team32.ong.repository.CategoryRepository;
import com.team32.ong.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryImplService implements CategoryService {

    @Autowired
    private CategoryRepository repo;


    @Override
    public Category save(Category category){
        return repo.save(category);
    }

}
