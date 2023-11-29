package ru.clevertec.bank.testData;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.bank.dto.UserDto;
import ru.clevertec.bank.entity.User;

@Data
@Builder(setterPrefix = "with")
public class UserTestData {

    @Builder.Default
    private java.util.UUID uuid = UtilTestConstant.UUID;
    @Builder.Default
    private String firstName = UtilTestConstant.firstName;
    @Builder.Default
    private String lastName = UtilTestConstant.lastName;
    @Builder.Default
    private String mail = UtilTestConstant.mail;
    @Builder.Default
    private int age = 20;

    public User buildUser() {
        return new User(uuid, firstName, lastName, mail, age);
    }

    public UserDto buildUserDto() {
        return new UserDto(null, firstName, lastName, mail, age);
    }

}
