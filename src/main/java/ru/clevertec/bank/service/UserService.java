package ru.clevertec.bank.service;

import java.util.Optional;
import java.util.UUID;
import ru.clevertec.bank.dto.UserDto;

public interface UserService {

    Optional<UUID> createUser(UserDto userDto);

    UserDto getUserById(UUID id);

    void updateUser(UserDto updatedUser);

    void deleteUser(UUID id);

}
