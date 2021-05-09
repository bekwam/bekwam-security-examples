package us.bekwam.security.examples.basic;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/public")
public class PublicResource {

    @Inject
    MessageBean messageBean;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String helloWorld() { return messageBean.helloWorld(); }
}
