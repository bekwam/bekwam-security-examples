package us.bekwam.security.examples.ws.basic;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ServerEndpoint("/hw")
public class SecuredHelloWorldEndpoint {

    // waits for message "dummy", responds with "Hello, World!"
    @OnMessage
    public void sayHW(Session session, String dummy) throws Exception {
        session.getBasicRemote().sendText(
                "Hello, World! " +
                        LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
    }
}

