
package com.hackeurope.feedspeak;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Calum
 */
public class ConcreteDBConnector extends DatabaseConnector {
    
    public void addUser(User newUser)
    {
        try {
            Connection connection = getConnection();
            
            String insertString = "INSERT INTO users VALUES (null, ?, ?, ?, ?);";
            PreparedStatement prepStatement = connection.prepareStatement(insertString);
            prepStatement.setString(1, newUser.getPhoneNumber());
            prepStatement.setString(2, newUser.getName());
            prepStatement.setString(3, newUser.getOauthToken());
            prepStatement.setString(4, newUser.getOauthTokenSecret());
            prepStatement.executeUpdate();
            
            closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ConcreteDBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public User getUserByPhoneNumber(String phoneNumber)
    {
        User newUser = new User();
        try {
            Connection connection = getConnection();
            
            String selectString = "SELECT * FROM users WHERE phone_number = ?;";
            PreparedStatement prepStatement = connection.prepareStatement(selectString);
            prepStatement.setString(1, phoneNumber);
            ResultSet resultSet = prepStatement.executeQuery();
            
            
            while (resultSet.next())
            {
                newUser.setId(resultSet.getInt("id"));
                newUser.setPhoneNumber(resultSet.getString("phone_number"));
                newUser.setName(resultSet.getString("name"));
                newUser.setOauthToken(resultSet.getString("oauth_token"));
                newUser.setOauthTokenSecret(resultSet.getString("oauth_token_secret"));
            }
            
            closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ConcreteDBConnector.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return newUser;
                
    }
    
    public List<String> getUsersSources(User user)
    {
        ArrayList<String> sources = new ArrayList<String>();
        try {
            Connection connection = getConnection();
            
            String selectString = "SELECT * FROM users "
                                + "JOIN user_sources ON users.user_id = user_sources.user_id "
                                + "JOIN sources ON user_sources.source_id = sources.source_id "
                                + "WHERE users.user_id = ?;";
            PreparedStatement prepStatement = connection.prepareStatement(selectString);
            prepStatement.setInt(1, user.getId());            
            ResultSet resultSet = prepStatement.executeQuery();
            
            
            while (resultSet.next())
            {
                sources.add(resultSet.getString("source_name"));
            }
            
            closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ConcreteDBConnector.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return sources;
    }
    
}
