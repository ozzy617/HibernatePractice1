package com.firsthib.service;

import com.firsthib.dao.UserRepository;
import com.firsthib.dto.UserCreateDto;
import com.firsthib.dto.UserReadDto;
import com.firsthib.mapper.UserCreateMapper;
import com.firsthib.mapper.UserReadMapper;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Optional;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateMapper userCreateMapper;

    public boolean delete(Integer id) {
        var maybeUser = userRepository.findById(id);
        maybeUser.ifPresent(user -> userRepository.delete(id));
        return maybeUser.isPresent();
    }

    public Optional<UserReadDto> findUserById(Integer id) {
        return userRepository.findById(id).map(userReadMapper::mapFrom);
    }

    public Integer create(UserCreateDto userDto) {
        var validationFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validationFactory.getValidator();
        var validationResult = validator.validate(userDto);
        if (!validationResult.isEmpty()) {
            throw new ConstraintViolationException(validationResult);
        }

        var userEntity = userCreateMapper.mapFrom(userDto);
        return userRepository.save(userEntity).getId();
    }
}
