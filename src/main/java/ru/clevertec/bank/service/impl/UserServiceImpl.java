package ru.clevertec.bank.service.impl;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import ru.clevertec.bank.dao.UserDao;
import ru.clevertec.bank.dto.UserDto;
import ru.clevertec.bank.entity.User;
import ru.clevertec.bank.mapper.UserMapper;
import ru.clevertec.bank.service.UserService;
import ru.clevertec.bank.validator.UserDtoValidator;

@Slf4j
public class UserServiceImpl implements UserService {

    private final UserMapper mapper;
    private final UserDao userDao;
    private final UserDtoValidator validator;

    public UserServiceImpl(UserMapper mapper, UserDao userDao, UserDtoValidator validator) {
        this.mapper = mapper;
        this.userDao = userDao;
        this.validator = validator;
    }

    @Override
    public Optional<UUID> createUser(UserDto userDto) {
        try {
            validator.validateUserDto(userDto);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
        User user = mapper.toUser(userDto);
        UUID id = userDao.createUser(user);
        Optional<UUID> result = Optional.ofNullable(id);
        return result;
    }

    @Override
    public UserDto getUserById(UUID id) {
        return mapper.toUserDto(userDao.getUserById(id)
            .orElseThrow(() -> new NoSuchElementException("No user with id " + id)));
    }

    @Override
    public void updateUser(UserDto updatedUser) {
        try {
            validator.validateUserDto(updatedUser);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return;
        }
        userDao.updateUser(mapper.toUser(updatedUser));
    }

    @Override
    public void deleteUser(UUID id) {
        userDao.deleteUser(id);
    }

}
