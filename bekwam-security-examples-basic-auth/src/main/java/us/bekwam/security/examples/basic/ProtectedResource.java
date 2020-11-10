package us.bekwam.security.examples.basic;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/protected")
public class ProtectedResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String secretMessage() { return "A Secret Message"; }
}
