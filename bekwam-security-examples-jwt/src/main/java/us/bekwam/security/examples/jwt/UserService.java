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

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

/**
 * @author carl
 */
@Stateless
public class UserService {

    private List<User> users = List.of(
            new User("carlw"),
            new User("marie")
    );

    public AuthenticateResponse authenticate(String username, String password) {

        Optional<User> user = users.stream().filter(u -> u.getUsername().equals(username)).findFirst();

        if( user.isPresent() ) {
            if( user.get().getPassword().equals(password) ) {
                return new AuthenticateResponse(true, user);
            } else {
                return new AuthenticateResponse(false, user);
            }
        }

        return new AuthenticateResponse(false, Optional.empty());
    }
}
