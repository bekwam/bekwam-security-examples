package us.bekwam.security.examples.ctx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private Logger log = LoggerFactory.getLogger(ItemsResource.class);

    @Inject
    private ItemBean itemBean;

    //
    // sc could be used to implement an auditing requirement at the
    // web layer
    //
    @Context
    private SecurityContext sc;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> getItems() {
        if( log.isDebugEnabled() ) {
            log.debug("[GET ITEMS] owner (at web layer) is {}",
                    sc.getUserPrincipal().getName());
        }
        return itemBean.findItemsByOwner();
    }
}
