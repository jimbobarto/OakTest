package uk.co.oaktest.database;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    Connection connection = null;
    String databasePath = "src/main/resources/oaktest.db";
    String absoluteDatabasePath;

    final static Logger logger = Logger.getLogger(Database.class);

    public Database() {
        try {
            File databaseFile = new File(this.databasePath);
            this.absoluteDatabasePath = databaseFile.getAbsolutePath();
            if (!databaseFile.exists()) {
                databaseFile.createNewFile();
            }
        }
        catch(IOException ioException) {
            logger.error("Could not create db file: " + ioException.getMessage());
        }
    }

    public ResultSet query(String query) {
        ResultSet resultSet = null;
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        }
        catch(SQLException sqlException) {
            logger.error("File probably doesn't exist: " + sqlException.getMessage());
        }

        return resultSet;
    }

    public void executeUpdate(String update) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(update);
        }
        catch(SQLException sqlException) {
            logger.error("Update failed: " + sqlException.getMessage());
        }
    }

    public Boolean checkTableExists(String tableName) {
        try {
            ResultSet results = query("SELECT name FROM sqlite_master WHERE type='table' AND name='" + tableName + "';");
            if (!results.isBeforeFirst() ) {
                return false;
            }
        }
        catch(SQLException sqlException) {
            logger.error("Issue when checking for table '" + tableName + "': " + sqlException.getMessage());
            return false;
        }
        return true;
    }

    public Boolean createTable(String createStatement) {
        try {
            Connection connection = getConnection();
            if (connection != null){
                Statement statement = connection.createStatement();
                statement.setQueryTimeout(10);  // set timeout to 30 sec.

                statement.executeUpdate(createStatement);
            }
            else {
                return false;
            }
        }
        catch(SQLException sqlException) {
            logger.error("File probably doesn't exist: " + sqlException.getMessage());
            return false;
        }

        return true;
    }

    private Connection getConnection() {
        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.absoluteDatabasePath);
        }
        catch(SQLException sqlException) {
            logger.error("File probably doesn't exist: " + sqlException.getMessage());
            return null;
        }

        return connection;
    }
}
