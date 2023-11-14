package ru.clevertec.bank.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.clevertec.bank.dao.UserDao;
import ru.clevertec.bank.entity.User;

public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public UUID createUser(User user) {
        String sql = "INSERT INTO clevertec_system.users (first_name, last_name, mail, age) VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getMail());
            ps.setInt(4, user.getAge());
            return ps;
        }, keyHolder);

        return (UUID) keyHolder.getKeys().get("id");
    }

    public Optional<User> getUserById(UUID id) {
        String sql = "SELECT * FROM clevertec_system.users WHERE id = ?";
        return Optional.ofNullable(
            jdbcTemplate.queryForObject(sql, new Object[]{id}, new UserRowMapper()));
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM clevertec_system.users";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    public void updateUser(User user) {
        String sql = "UPDATE clevertec_system.users SET first_name = ?, last_name = ?, mail = ?, age = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getFirstName(), user.getLastName(), user.getMail(),
            user.getAge(),
            user.getId());
    }

    public void deleteUser(UUID id) {
        String sql = "DELETE FROM clevertec_system.users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            User user = new User();
            user.setId(UUID.fromString(resultSet.getString("id")));
            user.setFirstName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));
            user.setMail(resultSet.getString("mail"));
            user.setAge(resultSet.getInt("age"));
            return user;
        }

    }

}
