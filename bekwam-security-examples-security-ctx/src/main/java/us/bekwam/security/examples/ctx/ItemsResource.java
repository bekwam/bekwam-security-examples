package us.bekwam.security.examples.ctx;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/items")
public class ItemsResource {

    @Inject
    private ItemBean itemBean;

    @Context
    private SecurityContext sc;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> geItems() {
        return itemBean.findItemsByOwner(sc.getUserPrincipal().getName());
    }
}
