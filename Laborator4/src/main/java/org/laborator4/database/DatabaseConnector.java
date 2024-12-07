package org.laborator4.database;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@ApplicationScoped
public class DatabaseConnector {

    private DataSource dataSource;

    @PostConstruct
    public void init() {
        try {
            javax.naming.Context context = new javax.naming.InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/MyDB");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize data source", e);
        }
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
