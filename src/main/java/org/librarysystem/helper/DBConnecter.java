package org.librarysystem.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnecter {

    private Connection con;

    // database bağlantısı oluşturduk
    public Connection connectDB() {
        try {
            this.con = DriverManager.getConnection(Config.DB_URL,Config.DB_USERNAME,Config.DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this.con;
    }

    // database bağlantımızı çağırır
    public static Connection getInstance() {
        DBConnecter db = new DBConnecter();
        return db.connectDB();
    }
}
