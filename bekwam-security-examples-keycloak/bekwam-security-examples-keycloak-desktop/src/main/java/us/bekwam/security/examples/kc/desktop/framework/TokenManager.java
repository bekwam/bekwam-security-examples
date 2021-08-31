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

import com.nimbusds.jose.JWSObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.Objects;

/**
 * @author carl
 */
public class TokenManager {

    private Logger log = LoggerFactory.getLogger(TokenManager.class);

    private String access;
    private String refresh;

    public TokenManager() {
    }

    public TokenManager(String access, String refresh) {
        this.access = access;
        this.refresh = refresh;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenManager that = (TokenManager) o;
        return Objects.equals(access, that.access) && Objects.equals(refresh, that.refresh);
    }

    @Override
    public int hashCode() {
        return Objects.hash(access, refresh);
    }

    public void fetchTokens(String username, String password, String client_id, String client_secret) throws IOException, InterruptedException {

        if( log.isDebugEnabled() ) {
            log.debug("[FETCH TOKENS]");
        }

        String payload = "username=" + username + "&password=" + password + "&client_id=" + client_id + "&client_secret=" + client_secret + "&grant_type=password";

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
        String refresh = jsonObject.getString("refresh_token");

        if( log.isDebugEnabled() ) {
            log.debug("access_token={}", token);
            log.debug("refresh_token={}", refresh);
        }

        setAccess(token);
        setRefresh(refresh);
    }

    public void execRefresh(String client_id, String client_secret) throws IOException, InterruptedException {

        if( log.isDebugEnabled() ) {
            log.debug("[EXEC REFRESH]");
        }

        String payload = "refresh_token=" + this.refresh + "&client_id=" + client_id + "&client_secret=" + client_secret + "&grant_type=refresh_token";

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

        if( log.isDebugEnabled() ) {
            log.debug("new access_token={}", token);
        }
        access = token;
    }

    public boolean isExpired() {
        JWSObject jwsObject;;
        try {
            jwsObject = JWSObject.parse(access);
            Long now = new Date().getTime() / 1_000; // in s
            Long exp = (Long)jwsObject.getPayload().toJSONObject().get("exp");
            if( log.isDebugEnabled() ) {
                if( log.isDebugEnabled() ) {
                    log.debug("[IS EXPIRED] now={}, exp={}", now, exp);
                }
            }
            return now >= exp;
        } catch (java.text.ParseException exc) {
            log.error("can't parse access", exc);
        }

        return false;
    }
}