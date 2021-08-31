/*
 * Copyright 2021 Bekwam, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package us.bekwam.security.examples.kc.desktop.tests;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;

/**
 * @author carl
 */
public class JMSTest {
    public static void main(String[] args) throws Exception {

        if( args.length != 2 ) {
            System.err.println("usage: java JMSClient username password");
            return;
        }

        final Properties jndiProperties = new Properties();

        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.wildfly.naming.client.WildFlyInitialContextFactory");
        jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");

        InitialContext context = new InitialContext(jndiProperties);

        ConnectionFactory connectionFactory = (ConnectionFactory)context.lookup("jms/RemoteConnectionFactory");

        Queue queue = (Queue)context.lookup("java:/jms/queue/KCQueue");

        try(
                Connection connection = connectionFactory.createConnection(args[0], args[1]);
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                MessageProducer messageProducer = session.createProducer(queue);
        ) {

            Message m = session.createTextMessage("from jms test client");
            messageProducer.send( m );


        } catch(JMSException exc) {
            String msg = "error adding message to queue 'KCQueue'";
            System.err.println( msg );
            exc.printStackTrace();
        }
    }
}
