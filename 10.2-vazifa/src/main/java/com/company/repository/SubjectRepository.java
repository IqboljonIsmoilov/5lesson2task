package com.company.repository;

import com.company.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Integer> {

    boolean existsByName(String name);

}
