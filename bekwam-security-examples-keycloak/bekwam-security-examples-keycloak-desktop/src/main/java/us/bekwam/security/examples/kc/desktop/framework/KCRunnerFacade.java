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

import org.wildfly.security.auth.client.AuthenticationContext;

import java.io.IOException;

/**
 * @author carl
 */
public class KCRunnerFacade {

    private String username;
    private String password;
    private String clientId;
    private String clientSecret;

    private TokenManager tokenManager = new TokenManager();
    private AuthContextFactory authContextFactory = new AuthContextFactory();

    public KCRunnerFacade(String[] args) {
        username = args[0];
        password = args[1];
        clientId = args[2];
        clientSecret = args[3];
    }

    public void init() throws IOException, InterruptedException {
        tokenManager.fetchTokens(username, password, clientId, clientSecret);
    }

    public AuthenticationContext createAuthContext() throws IOException, InterruptedException {

        if( tokenManager.isExpired() )
            tokenManager.execRefresh(clientId, clientSecret);

        return authContextFactory.createAuthContext(tokenManager.getAccess());
    }
}
