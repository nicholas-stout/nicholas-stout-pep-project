package DAO;
import Model.Message;
import Util.ConnectionUtil;

import java.lang.Thread.State;
import java.sql.*;
import java.util.*;

public class MessageDAO {
    /**
     * Method to insert a new message into the database
     * @param message the message to be inserted
     * @return the message that was inserted if successful
     */
    public Message insertMessage(Message message) {
        // create new connection
        Connection connection = ConnectionUtil.getConnection();
        
        try {
            String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();
            
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();

            if (pkeyResultSet.next()) {
                int generated_message_id = pkeyResultSet.getInt(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
         
        return null;
    }

    /**
     * Method to retrieve all messages from the database
     * @return a list of all messages in the database
     */
    public List<Message> retrieveAllMessages() {
        List<Message> messageList = new ArrayList<>();
        
        // Create new connection
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM message;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Message message = new Message(
                    resultSet.getInt("message_id"),
                    resultSet.getInt("posted_by"),
                    resultSet.getString("message_text"),
                    resultSet.getLong("time_posted_epoch")
                );

                messageList.add(message);
            }

            return messageList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }

    /**
     * Method to retrieve a message by its id
     * @param message_id the id of the message we're trying to find
     * @return the message, if it exists
     */
    public Message retrieveMessageById(int message_id) {
        // Create connection
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM message WHERE message_id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Message(
                    resultSet.getInt("message_id"),
                    resultSet.getInt("posted_by"),
                    resultSet.getString("message_text"),
                    resultSet.getLong("time_posted_epoch")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }

    /**
     * Method to delete a message by its id
     * @param message_id the id of the message we wish to delete
     */
    public void deleteMessageById(int message_id) {
        // Get new connection
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "DELETE FROM message WHERE message_id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method to update a message by its message_id
     * @param message_id the id of the message we wish to update
     * @param newMessageText the new text of the message
     */
    public void updateMessageById(int message_id, String newMessageText) {
        // Get new connection
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "UPDATE message SET message_text=? WHERE message_id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, newMessageText);
            preparedStatement.setInt(2, message_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method to retrieve all messages from a specific user
     * @param posted_by the id of the user whose messages we want
     * @return a list of all messages posted by this user
     */
    public List<Message> retrieveAllMessagesByUser(int posted_by) {
        List<Message> messageList = new ArrayList<>();
        
        // Get new connection
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM message WHERE posted_by=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, posted_by);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Message message = new Message(
                    resultSet.getInt("message_id"),
                    resultSet.getInt("posted_by"),
                    resultSet.getString("message_text"),
                    resultSet.getLong("time_posted_epoch")
                );

                messageList.add(message);
            }

            return messageList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }
}
