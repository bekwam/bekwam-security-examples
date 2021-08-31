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
package us.bekwam.security.examples.kc.desktop.tests;

import com.google.inject.Guice;
import com.google.inject.Injector;
import us.bekwam.security.examples.kc.commons.ejb.Echo;
import us.bekwam.security.examples.kc.desktop.framework.KCGuiceModule;
import us.bekwam.security.examples.kc.desktop.framework.ServiceLocator;

/**
 * @author carl
 */
public class EchoBeanTest {

    public static void main(String[] args) throws Exception {

        if( args.length != 4 ) {
            System.err.println("usage: java EchoBeanTest username password client_id, client_secret");
            System.exit(22);
        }

        /**
         * Guice setup
         */
        KCGuiceModule kcModule = new KCGuiceModule(args);

        Injector injector = Guice.createInjector(kcModule);

        /**
         * JNDI lookup
         */
        ServiceLocator serviceLocator = injector.getInstance(ServiceLocator.class);

        Echo echo = serviceLocator.lookupEcho();

        /**
         * Call business logic
         */
        String result = echo.echo("Returning a Value!");

        System.out.println("result=" + result);
    }
}
