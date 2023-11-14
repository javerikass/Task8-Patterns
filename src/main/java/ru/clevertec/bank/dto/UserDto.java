package ru.clevertec.bank.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserDto {

  private UUID id;
  private String firstName;
  private String lastName;
  private String mail;
  private int age;

}
