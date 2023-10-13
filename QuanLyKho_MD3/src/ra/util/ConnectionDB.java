package ra.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static ra.config.DataSource.*;
import static ra.notify.Notify.*;


public class ConnectionDB {
    public static Connection openConnection() {
        Connection conn = null;
        try {
            Class.forName(DRIVER);
            try {
                conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            } catch (SQLException e) {
                notifyError(e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            notifyError(e.getMessage());
        }
        return conn;
    }
    public static void closeConnection(Connection conn, CallableStatement callSt) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                notifyError(e.getMessage());
            }
        }
        if (callSt != null) {
            try {
                callSt.close();
            } catch (SQLException e) {
                notifyError(e.getMessage());
            }
        }

    }
}
