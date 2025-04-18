package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.*;
import Service.*;

public class SocialMediaController {
    private AccountService accountService = new AccountService();
    private MessageService messageService = new MessageService();
    
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        
        app.post("/register", this::postRegisterHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessagesHandler);
        app.get("/messages", this::getMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageIdHandler);
        app.patch("/messages/{message_id}", this::patchMessageIdHandler);
        app.get("/accounts/{account_id}/messages", this::getAccountIdMessagesHandler);

        return app;
    }

    /**
     * Handler for the endpoint POST localhost:8080/register. This handler will create a new Account and persist it to the database.
     * @param ctx Context object for information about HTTP request and response
     * @throws JsonProcessingException will be thrown if the JSON cannot be parsed
     */
    private void postRegisterHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);

        // check if Account was successfully added
        if (addedAccount != null) {
            ctx.json(addedAccount);
        } else {
            ctx.status(400);
        }
    }

    /**
     * Handler for the endpoint POST localhost:8080/login. This handler will process logins for users that already exist.
     * @param ctx Context object for information about HTTP request and response
     * @throws JsonProcessingException thrown if the JSON cannot be parsed 
     */
    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loggedInAccount = accountService.login(account);

        // check if Account was successfully logged in to
        if (loggedInAccount != null) {
            ctx.json(loggedInAccount);
        } else {
            ctx.status(401);
        }
    }

    /**
     * Handler for the endpoint POST localhost:8080/messages. This handler will create a new Message and persist it to the database.
     * @param ctx Context object for information about HTTP request and response
     * @throws JsonProcessingException thrownn if the JSON cannot be parsed
     */
    private void postMessagesHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);

        // check if Message was successfully added
        if (addedMessage != null) {
            ctx.json(addedMessage);
        } else {
            ctx.status(400);
        }
    }

    /**
     * Handler for the endpoint GET localhost:8080/messages. This handler will retrieve all messages from the database and convert them to JSON.
     * @param ctx Context object for information about HTTP request and response
     */
    private void getMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    /**
     * Handler for the endpoint GET localhost:8080/messages/{message_id}. This handler will retrieve a specific message by its message_id.
     * @param ctx Context object for information about HTTP request and response
     */
    private void getMessageIdHandler(Context ctx) {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(message_id);
        
        if (message != null) {
            ctx.json(message);
        }
    }

    /**
     * Handler for the endpoint DELETE localhost:8080/messages/{message_id}. This handler will delete a specific message by its message_id.
     * @param ctx Context object for information about HTTP request and response
     */
    private void deleteMessageIdHandler(Context ctx) {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessageById(message_id);

        if (deletedMessage != null) {
            ctx.json(deletedMessage);
        }
    }

    /**
     * Handler for the endpoint PATCH localhost:8080/messages/{message_id}. This handler will update a specific message by its message_id.
     * @param ctx Context object for information about HTTP request and response
     * @throws JsonProcessingException thrown if JSON cannot be parsed
     */
    private void patchMessageIdHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message updatedMessage = mapper.readValue(ctx.body(), Message.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        updatedMessage = messageService.updateMessageById(message_id, updatedMessage.getMessage_text());
        
        // check if Message successfully updated
        if (updatedMessage != null) {
            ctx.json(updatedMessage);
        } else {
            ctx.status(400);
        }
    }

    /**
     * Handler for the endpoint GET localhost:8080/accounts/{account_id}/messages. This handler will retrieve all messages from a specific user.
     * @param ctx Context object for information about HTTP request and response
     */
    private void getAccountIdMessagesHandler(Context ctx) {
        int account_id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> accountMessages = messageService.getAllMessagesByUser(account_id);
        ctx.json(accountMessages);
    }
}