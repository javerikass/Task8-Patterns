package ru.clevertec.bank.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import ru.clevertec.bank.dto.UserDto;

public interface UserService {

  Optional<UUID> createUser(UserDto user);

  UserDto getUserById(UUID id);

  List<UserDto> getAllUsers();

  void updateUser(UserDto updatedUser);

  void deleteUser(UUID id);
}
