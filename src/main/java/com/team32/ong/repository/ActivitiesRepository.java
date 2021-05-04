package com.team32.ong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.team32.ong.model.Activities;

@Repository
public interface ActivitiesRepository extends JpaRepository<Activities, Long>{

}
