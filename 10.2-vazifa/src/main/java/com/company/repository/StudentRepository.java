package com.company.repository;

import com.company.entity.StudentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {


    Page<StudentEntity> findAllByGroup_Faculty_UniversityId(Integer group_faculty_university_id, Pageable pageable);

}
