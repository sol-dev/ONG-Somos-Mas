package com.team32.ong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import com.team32.ong.model.Slide;

public interface SlideRepository extends JpaRepository<Slide, Long> {

    @Query(value = "SELECT * FROM slides WHERE organization_id = :id ORDER BY position", nativeQuery = true)
    List<Slide> findAllSlidesByOrganizationId(Long id);

    @Query(value = "SELECT DISTINCT o.name FROM slides s INNER JOIN organization o ON o.id = s.organization_id ORDER BY o.name", nativeQuery = true)
    List<String> findAllOrganizationsName();

    @Query(value = "SELECT * FROM slides s INNER JOIN organization o ON o.id = s.organization_id WHERE o.name = :name ORDER BY o.name ASC,s.position ASC", nativeQuery = true)
    List<Slide> findAllSlidesByOrganizationName(String name);

}
