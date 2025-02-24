package com.fortvision;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionsSQL {

    private static final Logger LOGGER = Logger.getLogger(ConnectionsSQL.class.getName());

    public static void cleanOldDataFromDatabases(String serverUrl, String user, String password) {
        try (Connection connection = DriverManager.getConnection(serverUrl, user, password)) {
            if (connection != null) {
                try (Statement stmt = connection.createStatement();
                     ResultSet rs = stmt.executeQuery("SHOW DATABASES")) {
                    while (rs.next()) {
                        String dbName = rs.getString(1);
                        if (!dbName.equalsIgnoreCase("information_schema")) {
                            cleanOldDataFromDatabase(serverUrl + dbName, user, password);
                        }
                    }
                }
            } else {
                LOGGER.severe("Failed to make connection.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Exception", e);
        }
    }

    public static void cleanOldDataFromDatabase(String url, String user, String password) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            if (connection != null) {
                DatabaseMetaData metaData = connection.getMetaData();
                try (ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"})) {
                    while (tables.next()) {
                        String tableName = tables.getString("TABLE_NAME");
                        if (!tableName.contains("template") &&
                                !tableName.equalsIgnoreCase("users") &&
                                !tableName.contains("account") &&
                                hasRequiredColumn(metaData, tableName)) {
                            deleteOldData(connection, tableName);
                        }
                    }
                }
            } else {
                LOGGER.severe("Failed to make connection to database: " + url);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Exception", e);
        }
    }

    private static boolean hasRequiredColumn(DatabaseMetaData metaData, String tableName) {
        try (ResultSet columnsCreatedAt = metaData.getColumns(null, null, tableName, "created_at");
             ResultSet columnsDate = metaData.getColumns(null, null, tableName, "date");
             ResultSet columnsStartedData = metaData.getColumns(null, null, tableName, "started_data")) {
            return columnsCreatedAt.next() || columnsDate.next() || columnsStartedData.next();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Exception", e);
        }
        return false;
    }

    private static void deleteOldData(Connection connection, String tableName) {
        String query = String.format(
                "DELETE FROM %s WHERE " +
                        "(STR_TO_DATE(created_at, '%%Y-%%m-%%d') < DATE_SUB(NOW(), INTERVAL 1 YEAR) " +
                        "OR created_at < UNIX_TIMESTAMP(DATE_SUB(NOW(), INTERVAL 1 YEAR)) " +
                        "OR created_at < DATE_SUB(NOW(), INTERVAL 1 YEAR)) " +
                        "OR (STR_TO_DATE(date, '%%Y-%%m-%%d') < DATE_SUB(NOW(), INTERVAL 1 YEAR) " +
                        "OR date < UNIX_TIMESTAMP(DATE_SUB(NOW(), INTERVAL 1 YEAR)) " +
                        "OR date < DATE_SUB(NOW(), INTERVAL 1 YEAR)) " +
                        "OR (STR_TO_DATE(started_data, '%%Y-%%m-%%d') < DATE_SUB(NOW(), INTERVAL 1 YEAR) " +
                        "OR started_data < UNIX_TIMESTAMP(DATE_SUB(NOW(), INTERVAL 1 YEAR)) " +
                        "OR started_data < DATE_SUB(NOW(), INTERVAL 1 YEAR))", tableName);
        try (Statement stmt = connection.createStatement()) {
            int rowsDeleted = stmt.executeUpdate(query);
            LOGGER.info("Deleted " + rowsDeleted + " rows from table: " + tableName);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Exception", e);
        }
    }

    public static void main(String[] args) {
        String serverUrl = "jdbc:mysql://fortvision.cluster-ro-c1pinvi6ti7x.eu-west-1.rds.amazonaws.com:3306/";
        String user = "fortvision_admin";
        String password = "aYLm3Z2am16jjKSqq501HahnBjKd0h";
        cleanOldDataFromDatabases(serverUrl, user, password);
    }
}
