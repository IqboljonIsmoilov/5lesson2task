package com.company.controller;

import com.company.dto.*;
import com.company.entity.*;
import com.company.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentRepository studentRepository;


    @GetMapping("/allStudents")
    public List<StudentDto> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::generateStudentDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/getStudentById/{id}")
    public StudentDto getStudentById(@PathVariable Integer id) {
        Optional<StudentEntity> optional = studentRepository.findById(id);
        return optional.map(this::generateStudentDto).orElse(null);
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Integer id) {
        StudentDto dto = getStudentById(id);
        if (dto != null) {
            studentRepository.deleteById(id);
            return "Student deleted successfully";
        }
        return "Student not found";
    }

    @PostMapping("/create")
    public String saveStudent(@RequestBody StudentDto dto) {
        studentRepository.save(generateStudent(dto));
        return "Student saved successfully";
    }

    @PostMapping("/update/{id}")
    public String editStudent(@PathVariable Integer id, @RequestBody StudentDto dto) {
        Optional<StudentEntity> optional = studentRepository.findById(id);
        if (optional.isPresent()) {
            StudentEntity student = optional.get();
            student.setFirstName(dto.getFirstName());
            student.setLastName(dto.getLastName());
            student.setAddress(generateAddress(dto.getAddressDto()));
            student.setGroup(generateMyGroup(dto.getMyGroupDto()));
            student.setSubjects(
                    dto.getSubjectDtos().stream()
                            .map(this::generateSubject)
                            .collect(Collectors.toList())
            );
            studentRepository.save(student);
            return "Student edited successfully";
        }
        return "Student not found";
    }

    private StudentEntity generateStudent(StudentDto dto) {
        return new StudentEntity(
                dto.getId(),
                dto.getFirstName(),
                dto.getLastName(),
                generateAddress(dto.getAddressDto()),
                generateMyGroup(dto.getMyGroupDto()),
                dto.getSubjectDtos().stream().map(this::generateSubject).collect(Collectors.toList())
        );
    }

    private SubjectEntity generateSubject(SubjectDto dto) {
        return new SubjectEntity(
                dto.getId(),
                dto.getName()
        );
    }

    private GroupEntity generateMyGroup(MyGroupDto dto) {
        return new GroupEntity(
                dto.getId(),
                dto.getName(),
                generateFaculty(dto.getMyFacultyDto())
        );
    }

    private FacultyEntity generateFaculty(MyFacultyDto dto) {
        return new FacultyEntity(
                dto.getId(),
                dto.getName(),
                generateUniversity(dto.getMyUniversityDto())
        );
    }

    private UniversityEntity generateUniversity(MyUniversityDto dto) {
        return new UniversityEntity(
                dto.getId(),
                dto.getName(),
                generateAddress(dto.getAddressDto())
        );
    }

    private AddressEntity generateAddress(AddressDto dto) {
        return new AddressEntity(
                dto.getId(),
                dto.getCity(),
                dto.getDistrict(),
                dto.getStreet()
        );
    }


    private StudentDto generateStudentDto(StudentEntity student) {
        return new StudentDto(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                generateAddressDto(student.getAddress()),
                generateMyGroupDto(student.getGroup()),
                student.getSubjects().stream()
                        .map(this::generateSubjectDto)
                        .collect(Collectors.toList())
        );
    }

    private SubjectDto generateSubjectDto(SubjectEntity subject) {
        return new SubjectDto(
                subject.getId(),
                subject.getName()
        );
    }

    private MyGroupDto generateMyGroupDto(GroupEntity group) {
        return new MyGroupDto(
                group.getId(),
                group.getName(),
                generateMyFacultyDto(group.getFaculty())
        );
    }

    private MyFacultyDto generateMyFacultyDto(FacultyEntity faculty) {
        return new MyFacultyDto(
                faculty.getId(),
                faculty.getName(),
                generateMyUniversityDto(faculty.getUniversity())
        );
    }

    private MyUniversityDto generateMyUniversityDto(UniversityEntity university) {
        return new MyUniversityDto(
                university.getId(),
                university.getName(),
                generateAddressDto(university.getAddress())
        );
    }

    private AddressDto generateAddressDto(AddressEntity address) {
        return new AddressDto(
                address.getId(),
                address.getCity(),
                address.getDistrict(),
                address.getStreet()
        );
    }

    @GetMapping("/forMinistry")
    public Page<StudentEntity> getStudentListForMinistry(@RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<StudentEntity> studentPage = studentRepository.findAll(pageable);
        return studentPage;
    }

    @GetMapping("/forUniversity/{universityId}")
    public Page<StudentEntity> getStudentListForUniversity(@PathVariable Integer universityId,
                                                           @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<StudentEntity> studentPage = studentRepository.findAllByGroup_Faculty_UniversityId(universityId, pageable);
        return studentPage;
    }

}
