/*
 * Copyright 2021 Bekwam, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package us.bekwam.security.examples.kc.desktop.framework;

import com.google.inject.Inject;
import com.google.inject.Provider;
import us.bekwam.security.examples.kc.commons.ejb.Capitalize;
import us.bekwam.security.examples.kc.commons.ejb.Echo;
import us.bekwam.security.examples.kc.desktop.capitalize.CapitalizeKCDecorator;
import us.bekwam.security.examples.kc.desktop.echo.EchoKCDecorator;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.util.Hashtable;

/**
 * @author carl
 */
public class ServiceLocator {

    @Inject
    Provider<EchoKCDecorator> ekcdProvider;

    @Inject
    Provider<CapitalizeKCDecorator> ckcdProvider;

    public Echo lookupEcho() throws NamingException, IOException, InterruptedException {
        final Hashtable<String, String> jndiProperties = new Hashtable<>();
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.wildfly.naming.client.WildFlyInitialContextFactory");
        jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        final Context context = new InitialContext(jndiProperties);

        Echo ejb = (Echo) context.lookup("ejb:bekwam-security-examples-keycloak-ear/kc-ejb/EchoBean!us.bekwam.security.examples.kc.commons.ejb.Echo");

        // wrap
        Echo ejbKC = ekcdProvider.get();
        ((EchoKCDecorator)ejbKC).init(ejb);

        return ejbKC;
    }

    public Capitalize lookupCapitalize() throws NamingException, IOException, InterruptedException {
        final Hashtable<String, String> jndiProperties = new Hashtable<>();
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.wildfly.naming.client.WildFlyInitialContextFactory");
        jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        final Context context = new InitialContext(jndiProperties);

        Capitalize ejb = (Capitalize) context.lookup("ejb:bekwam-security-examples-keycloak-ear/kc-ejb/CapitalizeBean!us.bekwam.security.examples.kc.commons.ejb.Capitalize");

        Capitalize ejbKC = ckcdProvider.get();
        ((CapitalizeKCDecorator)ejbKC).init(ejb);

        return ejbKC;
    }
}
