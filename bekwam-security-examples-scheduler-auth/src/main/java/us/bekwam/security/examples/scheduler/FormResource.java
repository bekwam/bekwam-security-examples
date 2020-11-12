package us.bekwam.security.examples.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/forms")
public class FormResource {

    private Logger log = LoggerFactory.getLogger(FormResource.class);

    @Inject
    private FormBean formBean;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Form> getAllForms() { return formBean.findAllForms(); }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void postForm(@FormParam("mydata") String mydata) {
        if( log.isDebugEnabled() ) {
            log.debug("[POST FORM] mydata={}", mydata);
        }
        formBean.receiveForm(new Form(mydata));
    }
}
