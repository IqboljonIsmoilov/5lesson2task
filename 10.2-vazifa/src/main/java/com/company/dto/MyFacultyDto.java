package com.company.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyFacultyDto {
    private Integer id;

    private String name;

    private MyUniversityDto myUniversityDto;
}
