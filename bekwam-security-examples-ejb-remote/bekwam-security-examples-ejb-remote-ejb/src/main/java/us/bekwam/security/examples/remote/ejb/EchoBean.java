package us.bekwam.security.examples.remote.ejb;

import org.jboss.ejb3.annotation.SecurityDomain;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 * @author carl
 */
@Remote(Echo.class)
@SecurityDomain("RemotingAppSecurityDomain")
@RolesAllowed("remotinguser")
@Stateless
public class EchoBean implements Echo {
    @Override
    public String echo(String input) {
        return input;
    }
}
