/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hackeurope.feedspeak;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Neil
 */
public class DatabaseConnector {

    private DataSource ds;
    protected Connection connect;
    private Statement statement;

    public DatabaseConnector() {
        String dbname = "jdbc/FeedSpeakDB";

        try {
            ds = (DataSource) new InitialContext().lookup("java:comp/env/" + dbname);
        } catch (NamingException e) {
            System.err.println(dbname + " is missing: " + e.toString());
        }
    }

    public Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            System.err.println("Error while connecting to database: " + e.toString());
        }
        return null;
    }

    /*
     * Connects to the database and issues the command
     * @param command which is executed by the database
     */
    public boolean execute(String command) {
        try {
            connect = getConnection();
            statement = connect.createStatement();
            statement.execute(command);
        } catch (SQLException e) {
            System.err.println("Error while executing SQL statement" + e.toString());
            return false;
        } finally {
            closeConnection();
        }
        return true;
    }

    public void closeConnection() {
        try {
            if (connect != null)
                connect.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
