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
package us.bekwam.security.examples.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

/**
 * @author carl
 */
@Path("/token")
public class TokenResource {

    private final Logger log = LoggerFactory.getLogger(TokenResource.class);

    @Inject
    UserService userService;

    @Inject
    JwtManager jwtManager;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response getToken(
            @FormParam("username") String username,
            @FormParam("password") String password
    ) {

        if( log.isDebugEnabled() ) {
            log.debug("[LOGIN] username={}", username);
        }

        AuthenticateResponse authenticateResponse =
                userService.authenticate(username, password);

        if( authenticateResponse.getAuthorized() ) {
            Optional<User> user = authenticateResponse.getUser();
            if( user.isPresent() ) {
                try {
                    String token = jwtManager.createJwt(
                            user.get().getUsername(),
                            user.get().getRoles().toArray(new String[0])
                    );

                    return Response
                            .ok()
                            .entity(
                                new GetTokenResponse(true, token, null)
                            )
                            .build();

                } catch(Exception exc) {
                    log.error("error calling createJwt", exc);
                    return Response
                            .serverError()
                            .entity(
                                new GetTokenResponse(false, null,
                                        exc.getClass().getName() + ": " + exc.getMessage())
                            )
                            .build();
                }
            }
        }

        return Response
                .status(Response.Status.UNAUTHORIZED)
                .build();
    }
}
