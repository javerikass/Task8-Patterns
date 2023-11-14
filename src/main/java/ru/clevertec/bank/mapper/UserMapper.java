package ru.clevertec.bank.mapper;

import java.util.List;
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

  /**
   * Маппит список User в список UserDto
   *
   * @param users - список User для маппинга
   * @return новый продукт
   */
  List<UserDto> toUserDtoList(List<User> users);
}
