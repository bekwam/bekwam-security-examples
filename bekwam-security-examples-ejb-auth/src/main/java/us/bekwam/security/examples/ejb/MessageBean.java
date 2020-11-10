package us.bekwam.security.examples.ejb;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

@Stateless
public class MessageBean {

    @PermitAll
    public String helloWorld() { return "Hello, World!"; }

    @RolesAllowed("user")
    public String secretMessage() { return "A Secret Message"; }
}
