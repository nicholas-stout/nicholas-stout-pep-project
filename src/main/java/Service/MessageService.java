package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.*;

import DAO.AccountDAO;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO = new AccountDAO();

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
     * TODO add logic
     * This method will attempt to persist a message to the database. It will first verify if the message is valid.
     * @param message the message we wish to add
     * @return the persisted message, if successful, null otherwise
     */
    public Message addMessage(Message message) {
        return null;
    }

    /**
     * TODO Add logic
     * This method will simply return all messages in our database
     * @return a list of every message is the database. The list will be empty if no messages have been added
     */
    public List<Message> getAllMessages() {
        return null;
    }

    /**
     * TODO Add logic
     * This method will return a message based on its message_id
     * @param message_id The message_id of the message we wish to retrieve
     * @return The message if it exists, null otherwise
     */
    public Message getMessageById(int message_id) {
        return null;
    }

    /**
     * TODO Add logic
     * This method will delete a message based on its message_id, and will return it.
     * @param message_id The id of the message we wish to delete
     * @return The deleted message if it exists, null otherwise
     */
    public Message deleteMessageById(int message_id) {
        return null;
    }

    /**
     * TODO Add logic
     * This method will update a message's text based on its message_id
     * @param message_id The id of the message we wish to update
     * @return The updated message if the original message exists and the new message is valid, null otherwise
     */
    public Message updateMessageById(int message_id) {
        return null;
    }

    /**
     * TODO Add logic
     * @param posted_by The id of the user whose messages we wish to query
     * @return A list of all messages posted by this user 
     */
    public List<Message> getAllMessagesByUser(int posted_by) {
        return null;
    }

    // TODO Add validation methods
}
