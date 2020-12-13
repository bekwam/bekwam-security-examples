package us.bekwam.security.examples.basic2;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/public")
public class PublicResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String helloWorld() { return "Hello, World! (2)"; }
}
