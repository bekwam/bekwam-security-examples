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

import java.util.Optional;

/**
 * @author carl
 */
public class AuthenticateResponse {

    private final Boolean authorized;
    private final Optional<User> user;

    public AuthenticateResponse(Boolean authorized, Optional<User> user) {
        this.authorized = authorized;
        this.user = user;
    }

    public Boolean getAuthorized() {
        return authorized;
    }

    public Optional<User> getUser() {
        return user;
    }
}
