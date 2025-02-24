package DAO;
import Model.Message;
import Util.ConnectionUtil;
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
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();
            return message;
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

    public void deleteMessageById(int message_id) {
        
    }

    public Message updateMessageById(int message_id, String newMessageText) {
        return null;
    }

    public List<Message> retrieveAllMessagesByUser(int user_id) {
        return null;
    }
}
