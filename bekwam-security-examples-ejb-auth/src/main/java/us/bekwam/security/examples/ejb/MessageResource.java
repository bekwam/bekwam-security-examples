package us.bekwam.security.examples.ejb;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class MessageResource {

    @Inject
    private MessageBean messageBean;

    @GET
    @Path("/public")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloWorld() { return messageBean.helloWorld(); }

    @GET
    @Path("/protected")
    @Produces(MediaType.TEXT_PLAIN)
    public String secretMessage() { return messageBean.secretMessage(); }
}
