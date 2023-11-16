package ru.clevertec.bank.service.impl;

import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import ru.clevertec.bank.dao.UserDao;
import ru.clevertec.bank.dao.impl.UserDaoImpl;
import ru.clevertec.bank.dto.UserDto;
import ru.clevertec.bank.entity.User;
import ru.clevertec.bank.mapper.UserMapper;
import ru.clevertec.bank.mapper.UserMapperImpl;
import ru.clevertec.bank.service.UserService;
import ru.clevertec.bank.validator.UserDtoValidator;
import ru.clevertec.bank.validator.impl.UserDtoValidatorImpl;

@Slf4j
public class UserServiceImpl implements UserService {

    private final UserMapper mapper;
    private final UserDao userDao;
    private final UserDtoValidator validator;


    public UserServiceImpl() {
        this.mapper = new UserMapperImpl();
        this.userDao = new UserDaoImpl();
        this.validator = new UserDtoValidatorImpl();
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
        UUID user1 = userDao.createUser(user);
        Optional<UUID> result = Optional.ofNullable(user1);
        return result;
    }

    @Override
    public UserDto getUserById(UUID id) {
        return mapper.toUserDto(userDao.getUserById(id).orElseThrow());
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
