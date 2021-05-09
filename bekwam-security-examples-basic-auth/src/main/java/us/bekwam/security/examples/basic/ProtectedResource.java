package us.bekwam.security.examples.basic;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/protected")
public class ProtectedResource {

    @Inject
    MessageBean messageBean;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String secretMessage() { return messageBean.secretMessage(); }
}
