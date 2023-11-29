package ru.clevertec.bank.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionPool {

    private static HikariDataSource dataSource;

    private ConnectionPool() {
    }

    public static synchronized HikariDataSource getDataSource() {
        if (dataSource == null) {
            HikariConfig config = new HikariConfig();
            config.setDriverClassName(PropertiesManager.getProperty(Util.DB_DRIVER));
            config.setJdbcUrl(PropertiesManager.getProperty(Util.DB_URL_KEY));
            config.setUsername(PropertiesManager.getProperty(Util.DB_USER_KEY));
            config.setPassword(PropertiesManager.getProperty(Util.DB_PASS_KEY));
            dataSource = new HikariDataSource(config);
        }
        return dataSource;
    }

    public static synchronized void closeDataSource() {
        if (dataSource != null) {
            dataSource.close();
            dataSource = null;
        }
    }

}
