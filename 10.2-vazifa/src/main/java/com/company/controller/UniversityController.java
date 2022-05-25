package com.company.controller;

import com.company.dto.UniversityDto;
import com.company.entity.AddressEntity;
import com.company.entity.UniversityEntity;
import com.company.repository.AddressRepository;
import com.company.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UniversityController {
    @Autowired
    UniversityRepository universityRepository;
    @Autowired
    AddressRepository addressRepository;

    @RequestMapping(value = "/university", method = RequestMethod.GET)
    public List<UniversityEntity> getUniversities() {
        List<UniversityEntity> universityList = universityRepository.findAll();
        return universityList;
    }


    @RequestMapping(value = "/university", method = RequestMethod.POST)
    public String addUniversity(@RequestBody UniversityDto universityDto) {

        AddressEntity address = new AddressEntity();
        address.setCity(universityDto.getCity());
        address.setDistrict(universityDto.getDistrict());
        address.setStreet(universityDto.getStreet());
        AddressEntity savedAddress = addressRepository.save(address);

        UniversityEntity university = new UniversityEntity();
        university.setName(universityDto.getName());
        university.setAddress(savedAddress);
        universityRepository.save(university);

        return "University added";
    }

    @RequestMapping(value = "/university/{id}", method = RequestMethod.PUT)
    public String editUniversity(@PathVariable Integer id, @RequestBody UniversityDto universityDto) {
        Optional<UniversityEntity> optionalUniversity = universityRepository.findById(id);
        if (optionalUniversity.isPresent()) {
            UniversityEntity university = optionalUniversity.get();
            university.setName(universityDto.getName());


            AddressEntity address = university.getAddress();
            address.setCity(universityDto.getCity());
            address.setDistrict(universityDto.getDistrict());
            address.setStreet(universityDto.getStreet());
            addressRepository.save(address);

            universityRepository.save(university);
            return "University edited";
        }
        return "University not found";
    }


    @RequestMapping(value = "/university/{id}", method = RequestMethod.DELETE)
    public String deleteUniversity(@PathVariable Integer id) {
        universityRepository.deleteById(id);
        return "University deleted";
    }
}
