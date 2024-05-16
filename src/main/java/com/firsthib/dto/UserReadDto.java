package com.firsthib.dto;

import com.firsthib.entity.PersonalInfo;
import com.firsthib.entity.Role;

public record UserReadDto(Integer id,
                          PersonalInfo personalInfo,
                          String username,
                          Role role,
                          CompanyReadDto company){
}
