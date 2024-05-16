package com.firsthib.dto;

import com.firsthib.entity.PersonalInfo;
import com.firsthib.entity.Role;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public record UserCreateDto (
        @Valid
        PersonalInfo personalInfo,
                             @NotNull
                             String username,
                             Role role,
                             Integer companyId){
}
