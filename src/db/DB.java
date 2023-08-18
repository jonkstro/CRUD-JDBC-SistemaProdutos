package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {
    private static Connection conn = null;

    public static Properties lProperties() {
        try (FileInputStream fs = new FileInputStream("resources\\db.properties")) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }

    public static Connection gConnection() {
        if (conn == null) {
            Properties props = lProperties();
            String url = props.getProperty("url");
            String usernamen = props.getProperty("usernamen");
            String password = props.getProperty("password");

            try {
                conn = DriverManager.getConnection(url, usernamen, password);
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        return conn;
    }

}
