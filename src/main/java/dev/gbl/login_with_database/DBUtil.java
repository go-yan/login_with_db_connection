package dev.gbl.login_with_database;


import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
    public Connection connectionLink;

    public Connection getConnection() {
        String dbName = "login_db.db";
        String url = "jdbc:sqlite:" + Main.class.getResource(dbName);

        try {

            connectionLink = DriverManager.getConnection(url);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return connectionLink;
    }
}
