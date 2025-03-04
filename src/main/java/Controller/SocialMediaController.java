package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
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

    private void postRegisterHandler(Context ctx) {
        
    }

    private void postLoginHandler(Context ctx) {

    }

    private void postMessagesHandler(Context ctx) {

    }

    private void getMessagesHandler(Context ctx) {

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