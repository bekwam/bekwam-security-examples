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
package us.bekwam.security.examples.kc.rest;

import us.bekwam.security.examples.kc.commons.ejb.Echo;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * @author carl
 */
@Path("/echo")
public class EchoResource {

    @Inject
    Echo echo;

    @GET
    public String echo(String in) {
        return echo.echo(in);
    }
}
