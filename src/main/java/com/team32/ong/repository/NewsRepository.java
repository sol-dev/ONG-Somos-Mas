package com.team32.ong.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.team32.ong.model.News;


@Repository
public interface NewsRepository extends JpaRepository<News,Long> {
   
}
