package com.team32.ong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import com.team32.ong.model.Slide;

public interface SlideRepository extends JpaRepository<Slide, Long> {

    @Query(value = "SELECT * FROM slides WHERE organization_id = :id ORDER BY position", nativeQuery = true)
    List<Slide> findAllSlidesByOrganization(Long id);

}
