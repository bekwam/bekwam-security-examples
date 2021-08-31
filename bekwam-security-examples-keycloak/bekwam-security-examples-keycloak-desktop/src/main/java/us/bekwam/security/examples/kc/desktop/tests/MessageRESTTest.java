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

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * @author carl
 */
public class MessageRESTTest {

    public static void main(String[] args) throws Exception {

        if( args.length != 4 ) {
            System.err.println("usage: java MessageRESTTest username password client_id, client_secret");
            System.exit(22);
        }

        /**
         * Fetch token
         */
        String payload = "username=" + args[0] + "&password=" + args[1] + "&client_id=" + args[2] + "&client_secret=" + args[3] + "&grant_type=password";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8180/auth/realms/kc-plat-arch/protocol/openid-connect/token"))
                .header("Content-type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        String bd = response.body();

        JsonReader reader = Json.createReader(new StringReader(bd));

        JsonObject jsonObject = reader.readObject();

        String token = jsonObject.getString("access_token");

        /**
         * Issue service call
         */
        client = HttpClient.newHttpClient();
        request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/kc-rest/api/message"))
                .header("Authorization", "Bearer " + token)
                .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();
    }
}
