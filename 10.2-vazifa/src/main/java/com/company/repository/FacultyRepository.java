package com.company.repository;

import com.company.entity.FacultyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacultyRepository extends JpaRepository<FacultyEntity, Integer> {

    boolean existsByNameAndUniversityId(String name, Integer university_id);

    List<FacultyEntity> findAllByUniversityId(Integer university_id);


}
