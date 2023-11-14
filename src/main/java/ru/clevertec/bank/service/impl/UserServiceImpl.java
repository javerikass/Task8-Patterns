package ru.clevertec.bank.service.impl;

import java.util.List;
import java.util.UUID;
import ru.clevertec.bank.dao.UserDao;
import ru.clevertec.bank.dao.impl.UserDaoImpl;
import ru.clevertec.bank.dto.UserDto;
import ru.clevertec.bank.entity.User;
import ru.clevertec.bank.jdbc.ConnectionPool;
import ru.clevertec.bank.mapper.UserMapper;
import ru.clevertec.bank.mapper.UserMapperImpl;
import ru.clevertec.bank.service.UserService;

public class UserServiceImpl implements UserService {

  private final UserMapper mapper;
  private final UserDao userDao;

  public UserServiceImpl() {
    this.mapper = new UserMapperImpl();
    this.userDao = new UserDaoImpl(ConnectionPool.getDataSource());
  }

  @Override
  public UUID createUser(UserDto userDto) {
    User user = mapper.toUser(userDto);
    return userDao.createUser(user);
  }

  @Override
  public UserDto getUserById(UUID id) {
    return mapper.toUserDto(userDao.getUserById(id).orElseThrow());
  }

  @Override
  public List<UserDto> getAllUsers() {
    return mapper.toUserDtoList(userDao.getAllUsers());
  }

  @Override
  public void updateUser(UserDto updatedUser) {
    userDao.updateUser(mapper.toUser(updatedUser));
  }

  @Override
  public void deleteUser(UUID id) {
    userDao.deleteUser(id);
  }

}
