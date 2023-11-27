package ru.clevertec.bank.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.bank.dto.UserDto;
import ru.clevertec.bank.entity.User;

@Mapper
public interface UserMapper {

    /**
     * Маппит User в UserDTO
     *
     * @param user - DTO для маппинга
     * @return новый продукт
     */
    UserDto toUserDto(User user);

    /**
     * Маппит UserDTO в User
     *
     * @param userDto - DTO для маппинга
     * @return новый продукт
     */
    User toUser(UserDto userDto);

}
