package uk.co.oaktest.database;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class Database {

    Connection connection = null;

    final static Logger logger = Logger.getLogger(Database.class);

    public Database() {
        try {
            // create a database connection
            this.connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/oaktest.db");
            Statement statement = this.connection.createStatement();
            statement.setQueryTimeout(10);  // set timeout to 30 sec.

            statement.executeUpdate("drop table if exists person");
            statement.executeUpdate("create table person (id integer, name string)");
            statement.executeUpdate("insert into person values(1, 'leo')");
            statement.executeUpdate("insert into person values(2, 'yui')");
            ResultSet rs = statement.executeQuery("select * from person");
            while(rs.next()) {
                // read the result set
                logger.info("name = " + rs.getString("name"));
                logger.info("id = " + rs.getInt("id"));
            }
        }
        catch(SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            logger.error("File probably doesn't exist: " + e.getMessage());
        }
        finally {
            try {
                if(this.connection != null) {
                    this.connection.close();
                }
            }
            catch(SQLException e) {
                // connection close failed.
                logger.error("Close failed: " + e.getMessage());
            }
        }
    }

    public ResultSet query(String query) {
        ResultSet resultSet = null;
        try {
            Statement statement = this.connection.createStatement();
            resultSet = statement.executeQuery(query);
        }
        catch(SQLException sqlException) {
            logger.error("File probably doesn't exist: " + sqlException.getMessage());
        }

        return resultSet;
    }
}
