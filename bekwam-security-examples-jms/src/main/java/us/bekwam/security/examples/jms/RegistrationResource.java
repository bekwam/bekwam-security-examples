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
package us.bekwam.security.examples.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author carl
 */
@Path("/reg")
public class RegistrationResource {

    private Logger log = LoggerFactory.getLogger(RegistrationResource.class);

    @Inject
    RegistrationBean registrationBean;

    @Resource(mappedName="java:/ConnectionFactory")
    ConnectionFactory connectionFactory;

    @Resource(mappedName="java:/jms/queue/SecexInbound")
    Queue queue;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addRegistration(@FormParam("name") String name) {

        try(
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = session.createProducer(queue);
        ) {

            Message m = session.createObjectMessage(new Registration(name));
            messageProducer.send( m );

            return Response.accepted().build();

        } catch(JMSException exc) {
            String msg = "error adding message to queue 'SecexInbound'";
            log.error(msg, exc);
            return Response.serverError().type(MediaType.TEXT_PLAIN_TYPE).entity(msg).build();
        }
    }

    @GET
    public List<Registration> getRegistrations() {
        return registrationBean.findAll();
    }
}
