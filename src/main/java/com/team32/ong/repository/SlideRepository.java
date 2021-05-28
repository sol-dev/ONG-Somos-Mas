package com.team32.ong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.team32.ong.model.Slide;

@Repository
public interface SlideRepository extends JpaRepository<Slide, Long> {

    @Query(value = "SELECT o.slides FROM OrganizationEntity o WHERE o.name = :name")
    List<Slide> findAllSlidesByOrganizationName(String name);

    @Query("SELECT s.imageUrl FROM OrganizationEntity o JOIN Slide s ON o = s.organization WHERE o.id = :id ORDER BY s.order")
    List<String> findAllSlideUrlByOrganizationId(Long id);

    @Query("SELECT DISTINCT o.name FROM OrganizationEntity o JOIN o.slides")
    List<String> findAllOrganizationsName();

}
