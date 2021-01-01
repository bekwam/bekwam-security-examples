package us.bekwam.security.examples.ws.form;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ServerEndpoint("/hw")
public class SecuredHelloWorldEndpoint {

    // waits for message "dummy", responds with "Hello, Form!"
    @OnMessage
    public void sayHW(Session session, String dummy) throws Exception {
        session.getBasicRemote().sendText(
                "Hello, Form! " +
                        LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
    }

}
