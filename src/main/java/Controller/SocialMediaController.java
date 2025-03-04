package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.*;
import Service.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
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
        app.get("example-endpoint", this::exampleHandler); // TODO delete line
        
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
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
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

    private void getMessageIdHandler(Context ctx) {

    }

    private void deleteMessageIdHandler(Context ctx) {

    }

    private void patchMessageIdHandler(Context ctx) {

    }

    private void getAccountIdMessagesHandler(Context ctx) {

    }
}