package ru.clevertec.bank.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.bank.dao.UserDao;
import ru.clevertec.bank.dto.UserDto;
import ru.clevertec.bank.entity.User;
import ru.clevertec.bank.mapper.UserMapper;
import ru.clevertec.bank.testData.UserTestData;
import ru.clevertec.bank.testData.UtilTestConstant;
import ru.clevertec.bank.validator.UserDtoValidator;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserMapper mapper;
    @Mock
    private UserDao userDao;
    @Mock
    private UserDtoValidator validator;
    @InjectMocks
    private UserServiceImpl userService;


    @Test
    void testCreateUserShouldReturnId() {
        // given
        UserDto userDto = UserTestData.builder().build().buildUserDto();
        User user = UserTestData.builder().withUuid(null).build().buildUser();
        UUID userId = UtilTestConstant.UUID;

        when(mapper.toUser(userDto)).thenReturn(user);
        when(userDao.createUser(user)).thenReturn(userId);
        doNothing().when(validator).validateUserDto(userDto);

        // when
        Optional<UUID> actual = userService.createUser(userDto);

        // then
        assertTrue(actual.isPresent());
        assertEquals(userId, actual.get());

    }

    @Test
    void testCreateUserShouldReturnOptionalEmpty() {
        // given
        UserDto userDto = UserTestData.builder().build().buildUserDto();

        doThrow(new IllegalArgumentException("Invalid user data")).when(validator)
            .validateUserDto(userDto);

        // when
        Optional<UUID> result = userService.createUser(userDto);

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetUserByIdShouldReturnUser() {
        // given
        UUID userId = UtilTestConstant.UUID;
        User user = UserTestData.builder().build().buildUser();
        UserDto expected = UserTestData.builder().withUuid(userId).build().buildUserDto();

        when(userDao.getUserById(userId)).thenReturn(Optional.of(user));
        when(mapper.toUserDto(user)).thenReturn(expected);

        // when
        UserDto actual = userService.getUserById(userId);

        // then
        assertEquals(expected, actual);
    }

    @Test
    void testGetUserByIdShouldThrowException() {
        // given
        UUID userId = UUID.randomUUID();
        when(userDao.getUserById(userId)).thenReturn(Optional.empty());

        // when
        assertThrows(NoSuchElementException.class, () -> {
            userService.getUserById(userId);
        });

        // then
        verify(userDao).getUserById(userId);
        verifyNoMoreInteractions(mapper, userDao, validator);
    }

    @Test
    void testUpdateUserShouldInvokeUpdateUser() {
        // given
        UserDto updatedUserDto = UserTestData.builder().build().buildUserDto();
        User updatedUser = UserTestData.builder().build().buildUser();

        when(mapper.toUser(updatedUserDto)).thenReturn(updatedUser);
        doNothing().when(validator).validateUserDto(updatedUserDto);

        // when
        userService.updateUser(updatedUserDto);

        // then
        verify(userDao).updateUser(updatedUser);
    }

    @Test
    void testUpdateUserFailedValidation() {
        // given
        UserDto updatedUserDto = UserTestData.builder().build().buildUserDto();

        doThrow(new IllegalArgumentException("Invalid user data")).when(validator)
            .validateUserDto(updatedUserDto);

        // when
        userService.updateUser(updatedUserDto);

        // then
        verify(validator).validateUserDto(updatedUserDto);
        verifyNoInteractions(mapper, userDao);
    }

    @Test
    void testDeleteUser() {
        // given
        UUID userId = UtilTestConstant.UUID;

        // when
        userService.deleteUser(userId);

        // then
        verify(userDao).deleteUser(userId);

    }

}