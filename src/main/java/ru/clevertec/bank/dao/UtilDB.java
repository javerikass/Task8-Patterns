package ru.clevertec.bank.dao;

public final class UtilDB {

    private UtilDB() {
    }

    public static final String CREATE_USER = "INSERT INTO clevertec_system.users (first_name, last_name, mail, age) VALUES (?, ?, ?, ?)";
    public static final String GET_USER_BY_ID = "SELECT * FROM clevertec_system.users WHERE id = ?";

    public static final String UPDATE_USER = "UPDATE clevertec_system.users SET first_name = ?, last_name = ?, mail = ?, age = ? WHERE id = ?";

    public static final String DELETE_USER = "DELETE FROM clevertec_system.users WHERE id = ?";

}
