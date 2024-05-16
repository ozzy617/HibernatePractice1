package com.firsthib.mapper;

import com.firsthib.dto.CompanyReadDto;
import com.firsthib.entity.Company;

public class CompanyReadMapper implements Mapper<Company, CompanyReadDto>  {
    @Override
    public CompanyReadDto mapFrom(Company object) {
        return new CompanyReadDto(object.getId(),
                object.getName());
    }
}
