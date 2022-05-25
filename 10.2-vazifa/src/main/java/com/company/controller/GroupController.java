package com.company.controller;

import com.company.dto.GroupDto;
import com.company.entity.FacultyEntity;
import com.company.entity.GroupEntity;
import com.company.repository.FacultyRepository;
import com.company.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    GroupRepository groupRepository;
    @Autowired
    FacultyRepository facultyRepository;

    @GetMapping
    public List<GroupEntity> getGroups() {
        List<GroupEntity> groups = groupRepository.findAll();
        return groups;
    }


    @GetMapping("/byUniversityId/{universityId}")
    public List<GroupEntity> getGroupsByUniversityId(@PathVariable Integer universityId) {
        List<GroupEntity> allByFaculty_universityId = groupRepository.findAllByFaculty_UniversityId(universityId);
        List<GroupEntity> groupsByUniversityId = groupRepository.getGroupsByUniversityId(universityId);
        List<GroupEntity> groupsByUniversityIdNative = groupRepository.getGroupsByUniversityIdNative(universityId);
        return allByFaculty_universityId;
    }

    @PostMapping
    public String addGroup(@RequestBody GroupDto groupDto) {

        GroupEntity group = new GroupEntity();
        group.setName(groupDto.getName());

        Optional<FacultyEntity> optionalFaculty = facultyRepository.findById(groupDto.getFacultyId());
        if (!optionalFaculty.isPresent()) {
            return "Such faculty not found";
        }

        group.setFaculty(optionalFaculty.get());

        groupRepository.save(group);
        return "Group added";
    }


}
