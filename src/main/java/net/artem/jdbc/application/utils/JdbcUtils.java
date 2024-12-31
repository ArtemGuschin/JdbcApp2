package net.artem.jdbc.application.utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;

public class JdbcUtils {
    private static Connection connection;
    private static final String PROPERTIES_FILE = "src/main/resources/application.properties";

    private static Connection getConnection() {
        if (Objects.isNull(connection)) {
            try {
                Properties properties = new Properties();
                properties.load(Files.newInputStream(Paths.get(PROPERTIES_FILE)));

                String url = properties.getProperty("db.url");
                String username = properties.getProperty("db.username");
                String password = properties.getProperty("db.password");

                connection = DriverManager.getConnection(url, username, password);
            } catch (Throwable e) {
                System.exit(1);
            }
        }
        return connection;
    }

    public static PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return getConnection().prepareStatement(sql);
    }

    public static PreparedStatement getPreparedStatementWithKey(String sql) throws SQLException {
        return getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    }


}
