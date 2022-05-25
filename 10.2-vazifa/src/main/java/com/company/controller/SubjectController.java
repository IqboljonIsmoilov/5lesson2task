package com.company.controller;

import com.company.entity.SubjectEntity;
import com.company.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/subject")
public class SubjectController {
    @Autowired
    SubjectRepository subjectRepository;

    @RequestMapping(method = RequestMethod.POST)
    public String addSubject(@RequestBody SubjectEntity subject) {
        boolean existsByName = subjectRepository.existsByName(subject.getName());
        if (existsByName)
            return "This subject already exist";
        subjectRepository.save(subject);
        return "Subject added";
    }


    @GetMapping
    public List<SubjectEntity> getSubjects() {
        List<SubjectEntity> subjectList = subjectRepository.findAll();
        return subjectList;
    }


}
