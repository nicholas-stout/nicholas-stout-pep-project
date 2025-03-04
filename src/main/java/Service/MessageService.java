package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.*;

import DAO.AccountDAO;

public class MessageService {
    private MessageDAO messageDAO;

    /**
     * Default no-args constructor will initialize messageDAO
     */
    public MessageService() {
        messageDAO = new MessageDAO();
    }

    /**
     * Constructor where a MessageDAO object is provided
     * @param messageDAO the MessageDAO object we want messageDAO to be
     */
    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    /**
     * This method will attempt to persist a message to the database. It will first verify if the message is valid.
     * @param message the message we wish to add
     * @return the persisted message, if successful, null otherwise
     */
    public Message addMessage(Message message) {
        // Validate message
        if (!isValidMessage(message)) {
            return null;
        }

        return messageDAO.insertMessage(message);
    }

    /**
     * This method will simply return all messages in our database
     * @return a list of every message is the database. The list will be empty if no messages have been added
     */
    public List<Message> getAllMessages() {
        return messageDAO.retrieveAllMessages();
    }

    /**
     * This method will return a message based on its message_id
     * @param message_id The message_id of the message we wish to retrieve
     * @return The message if it exists, null otherwise
     */
    public Message getMessageById(int message_id) {
        return messageDAO.retrieveMessageById(message_id);
    }

    /**
     * This method will delete a message based on its message_id, and will return it.
     * @param message_id The id of the message we wish to delete
     * @return The deleted message if it exists, null otherwise
     */
    public Message deleteMessageById(int message_id) {
        // Create Message object that will contain the data of the message we wish to delete. It will be null if it does not exist.
        Message message = messageDAO.retrieveMessageById(message_id);
        
        // If the message does not exist, just return null. Only try to delete the message if we're sure it exists.
        if (message == null) {
            return null;
        } 
            
        messageDAO.deleteMessageById(message_id);
        return message;
    }

    /**
     * This method will update a message's text based on its message_id
     * @param message_id The id of the message we wish to update
     * @return The updated message if the original message exists and the new message is valid, null otherwise
     */
    public Message updateMessageById(int message_id, String newMessageText) {
        // Check if original message exists
        if (messageDAO.retrieveMessageById(message_id) == null) {
            return null;
        }

        // Check if new message text is valid
        if (!isValidMessageText(newMessageText)) {
            return null;
        }
        
        // update the message
        messageDAO.updateMessageById(message_id, newMessageText);
        return messageDAO.retrieveMessageById(message_id);
    }

    /**
     * @param posted_by The id of the user whose messages we wish to query
     * @return A list of all messages posted by this user 
     */
    public List<Message> getAllMessagesByUser(int posted_by) {
        return messageDAO.retrieveAllMessagesByUser(posted_by);
    }

    /**
     * This method will check if a Message object represents a valid message.
     * A message is valid if it is posted by a real user and its text is not empty and not more than 255 characters.
     * @param message The message we are validating
     * @return true or false based on whether the message is valid
     */
    private boolean isValidMessage(Message message) {
        return userExists(message.getPosted_by()) && isValidMessageText(message.getMessage_text());    
    }

    /**
     * This method will check if the user_id referenced by posted_by exists in the database.
     * @param posted_by The posted_by attribute of a Message object. It should point to a real user in the account table.
     * @return true or false depending on whether the user exists
     */
    private boolean userExists(int posted_by) {
        // Create AccountDAO object to query the account table
        AccountDAO accountDAO = new AccountDAO();
        
        return (accountDAO.getAccountById(posted_by) != null);
    }

    /**
     * This method will check if the message_text of a Message is valid (i.e., it is not empty and not more than 255 characters).
     * @param message_text The text of the message we wish to validate
     * @return true or false depending on whether the message_text is valid
     */
    private boolean isValidMessageText(String message_text) {
        return 0 < message_text.length() && message_text.length() <= 255;
    }
}
