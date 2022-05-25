package com.company.controller;

import com.company.dto.FacultyDto;
import com.company.entity.FacultyEntity;
import com.company.entity.UniversityEntity;
import com.company.repository.FacultyRepository;
import com.company.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/faculty")
public class FacultyController {

    @Autowired
    FacultyRepository facultyRepository;
    @Autowired
    UniversityRepository universityRepository;


    @GetMapping
    public List<FacultyEntity> getFaculties() {
        return facultyRepository.findAll();
    }

    @PostMapping
    public String addFaculty(@RequestBody FacultyDto facultyDto) {
        boolean exists = facultyRepository.existsByNameAndUniversityId(facultyDto.getName(), facultyDto.getUniversityId());
        if (exists)
            return "This university such faculty exist";
        FacultyEntity faculty = new FacultyEntity();
        faculty.setName(facultyDto.getName());
        Optional<UniversityEntity> optionalUniversity = universityRepository.findById(facultyDto.getUniversityId());
        if (!optionalUniversity.isPresent())
            return "University not found";
        faculty.setUniversity(optionalUniversity.get());
        facultyRepository.save(faculty);
        return "Faculty saved";
    }


    @GetMapping("/byUniversityId/{universityId}")
    public List<FacultyEntity> getFacultiesByUniversityId(@PathVariable Integer universityId) {
        List<FacultyEntity> allByUniversityId = facultyRepository.findAllByUniversityId(universityId);
        return allByUniversityId;
    }


    @DeleteMapping("/{id}")
    public String deleteFaculty(@PathVariable Integer id) {
        try {
            facultyRepository.deleteById(id);
            return "Faculty deleted";
        } catch (Exception e) {
            return "Error in deleting";
        }
    }


    @PutMapping("/{id}")
    public String editFaculty(@PathVariable Integer id, @RequestBody FacultyDto facultyDto) {
        Optional<FacultyEntity> optionalFaculty = facultyRepository.findById(id);
        if (optionalFaculty.isPresent()) {
            FacultyEntity faculty = optionalFaculty.get();
            faculty.setName(facultyDto.getName());
            Optional<UniversityEntity> optionalUniversity = universityRepository.findById(facultyDto.getUniversityId());
            if (!optionalUniversity.isPresent()) {
                return "University not found";
            }
            faculty.setUniversity(optionalUniversity.get());
            facultyRepository.save(faculty);
            return "Faculty edited";
        }
        return "Faculty not found";
    }


}
