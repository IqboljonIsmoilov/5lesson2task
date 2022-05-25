package com.company.repository;

import com.company.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupRepository extends JpaRepository<GroupEntity, Integer> {


    List<GroupEntity> findAllByFaculty_UniversityId(Integer faculty_university_id);


    @Query("select gr from groups gr where gr.faculty.university.id=:universityId")
    List<GroupEntity> getGroupsByUniversityId(Integer universityId);

    @Query(value = "select *\n" +
            "from groups g\n" +
            "         join faculty f on f.id = g.faculty_id\n" +
            "         join university u on u.id = f.university_id\n" +
            "where u.id=:universityId", nativeQuery = true)
    List<GroupEntity> getGroupsByUniversityIdNative(Integer universityId);

}
