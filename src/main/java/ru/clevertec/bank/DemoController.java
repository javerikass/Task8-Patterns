package ru.clevertec.bank;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import ru.clevertec.bank.dao.impl.UserDaoImpl;
import ru.clevertec.bank.dto.UserDto;
import ru.clevertec.bank.jdbc.ConnectionPool;
import ru.clevertec.bank.mapper.UserMapperImpl;
import ru.clevertec.bank.service.UserService;
import ru.clevertec.bank.service.impl.UserServiceImpl;
import ru.clevertec.bank.validator.impl.UserDtoValidatorImpl;

public class DemoController {

    private final UserService service = new UserServiceImpl(
        new UserMapperImpl(),
        new UserDaoImpl(),
        new UserDtoValidatorImpl());

    private final Gson gson = new Gson();


    public static void main(String[] args) {
        DemoController demoController = new DemoController();
        demoController.initBD();
        UUID uuid = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        String userDto = "{\n"
            + "\"id\": \"123e4567-e89b-12d3-a456-426614174000\",\n"
            + "\"firstName\": \"John\",\n"
            + "\"lastName\": \"Doe\",\n"
            + "\"mail\": \"johndoe@example.com\",\n"
            + "\"age\": 25\n"
            + "}";
        demoController.put(userDto);
        demoController.post(userDto);
        demoController.get(uuid);
        demoController.delete(uuid);
    }

    public UserDto get(UUID id) {
        return service.getUserById(id);
    }

    public void post(String jsonUser) {
        UserDto userDto = gson.fromJson(jsonUser, UserDto.class);
        service.createUser(userDto);
    }

    public void delete(UUID id) {
        service.deleteUser(id);
    }

    public void put(String jsonUser) {
        UserDto userDto = gson.fromJson(jsonUser, UserDto.class);
        service.updateUser(userDto);
    }

    public void initBD() {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getDataSource().getConnection();
            statement = connection.createStatement();

            InputStream inputStream = DemoController.class.getClassLoader()
                .getResourceAsStream("init.sql");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder script = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                script.append(line);
            }
            reader.close();
            statement.executeUpdate(script.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null && statement != null) {
                    statement.close();
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

}
