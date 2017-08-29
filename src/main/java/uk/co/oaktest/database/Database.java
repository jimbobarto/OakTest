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
    String lastError;

    final static Logger logger = Logger.getLogger(Database.class);

    public Database() {
        File databaseFile = new File(this.databasePath);
        this.absoluteDatabasePath = databaseFile.getAbsolutePath();
    }

    public ResultSet query(String query) {
        ResultSet resultSet = null;

        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException sqlException) {
            logError("File probably doesn't exist: " + sqlException.getMessage(), sqlException);
        }

        return resultSet;
    }

    public void closeResults(ResultSet results) {
        try {
            results.close();
        } catch (SQLException sqlException) {
            logError("Closing results failed: " + sqlException.getMessage(), sqlException);
        }
    }

    public Boolean executeUpdate(String update) {
        Boolean success = true;
        try {
            Connection connection = null;
            try {
                connection = getConnection();
                Statement statement = connection.createStatement();
                int rowCount = statement.executeUpdate(update);
                logger.info("Updated " + rowCount + " rows");
            }
            catch(SQLException sqlException) {
                logError("Update failed: " + sqlException.getMessage(), sqlException);
                success = false;
            }
            finally {
                if (connection != null) {
                    connection.close();
                }
                return success;
            }
        }
        catch (SQLException anotherSqlException) {
            logError("Couldn't even close the connection!: " + anotherSqlException.getMessage(), anotherSqlException);
            return false;
        }
    }

    public Boolean checkTableExists(String tableName) {
        try {
            ResultSet results = query("SELECT name FROM sqlite_master WHERE type='table' AND name='" + tableName + "';");
            if (!results.isBeforeFirst() ) {
                closeResults(results);
                return false;
            }
            closeResults(results);
        }
        catch(SQLException sqlException) {
            logError("Issue when checking for table '" + tableName + "': " + sqlException.getMessage(), sqlException);
            return false;
        }
        return true;
    }

    public Boolean createTable(String createStatement) {
        try {
            Connection connection = null;
            try {
                connection = getConnection();
                if (connection != null){
                    Statement statement = connection.createStatement();
                    statement.setQueryTimeout(10);  // set timeout to 30 sec.

                    int rowCount = statement.executeUpdate(createStatement);
                    logger.info("Created table (" + rowCount + ")");
                }
                else {
                    return false;
                }
            }
            catch(SQLException sqlException) {
                logError("File probably doesn't exist: " + sqlException.getMessage(), sqlException);
                return false;
            }
            finally {
                if (connection != null) {
                    connection.close();
                }
            }
        }
        catch (SQLException anotherSqlException) {
            logError("Couldn't even close the connection!: " + anotherSqlException.getMessage(), anotherSqlException);
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
            logError("File probably doesn't exist: " + sqlException.getMessage(), sqlException);
            return null;
        }


        return connection;
    }

    private void logError(String errorMessage, Exception exception) {
        logger.error(errorMessage);
        this.lastError = exception.getMessage();
    }

    public String getLastError() {
        return this.lastError;
    }
}
