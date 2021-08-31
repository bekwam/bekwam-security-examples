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
package us.bekwam.security.examples.kc.javafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.keycloak.adapters.installed.KeycloakInstalled;
import us.bekwam.security.examples.kc.Echo;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.swing.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * @author carl
 */
public class KeycloakFX extends Application {

    private Logger logger = Logger.getLogger("KeycloakFX");

    private KeycloakInstalled keycloak;
    private StringProperty messageProperty = new SimpleStringProperty();
    private StringProperty nameProperty = new SimpleStringProperty();
    private BooleanProperty loggedInProperty = new SimpleBooleanProperty();
    private StringProperty errorMessageProperty = new SimpleStringProperty();
    private StringProperty inputProperty = new SimpleStringProperty();
    private StringProperty resultProperty = new SimpleStringProperty();

    private String token;

    @Override
    public void init() throws Exception {
        keycloak = new KeycloakInstalled();
    }

    HBox buildHeader() {

        Label title = new Label("Keycloak FX Demo");

        Label name = new Label("");
        name.textProperty().bind(nameProperty);
        name.managedProperty().bind(nameProperty.isNotEmpty());
        name.visibleProperty().bind(nameProperty.isNotEmpty());

        Button login_b = new Button("Login");
        login_b.disableProperty().bind(loggedInProperty);
        login_b.setOnAction( this::login );

        Button logout_b = new Button("Logout");
        logout_b.setOnAction( this::logout );
        logout_b.disableProperty().bind(loggedInProperty.not());

        HBox buttons = new HBox(name, login_b, logout_b);
        buttons.setSpacing(4.0d);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        HBox.setHgrow(buttons, Priority.ALWAYS);

        HBox header = new HBox(
                title,
                buttons
        );
        header.setAlignment(Pos.CENTER_LEFT);

        return header;
    }

    HBox buildErrorMessage() {
        Label errorMessage_lbl = new Label();
        errorMessage_lbl.textProperty().bind(errorMessageProperty);
        errorMessage_lbl.setTextFill(Color.WHITE);

        HBox hbox = new HBox(
                errorMessage_lbl
        );
        hbox.setPadding(new Insets(20.0d));
        hbox.setBackground(
                new Background(
                        new BackgroundFill(Color.RED,
                                           CornerRadii.EMPTY,
                                           Insets.EMPTY)
                )
        );
        hbox.managedProperty().bind(errorMessageProperty.isNotEmpty());
        hbox.visibleProperty().bind(errorMessageProperty.isNotEmpty());
        return hbox;
    }

    VBox buildMessageContent() {

        Button msg_b = new Button("Fetch");
        msg_b.setOnAction(this::fetchMessage);

        TextField msg_tf = new TextField();
        msg_tf.setEditable(false);
        msg_tf.textProperty().bind(messageProperty);

        return new VBox(
                10.0d,
                msg_b,
                new Label("Message"),
                msg_tf
        );
    }

    VBox buildEchoContent() {

        Button echo_b = new Button("Echo");
        echo_b.setOnAction(this::echo);

        TextField input_tf = new TextField();
        input_tf.setEditable(true);
        inputProperty.bind(input_tf.textProperty());

        TextField result_tf = new TextField();
        result_tf.setEditable(false);
        result_tf.textProperty().bind(resultProperty);

        return new VBox(
                10.0d,
                echo_b,
                new Label("Input"),
                input_tf,
                new Label("Result"),
                result_tf
        );
    }

    @Override
    public void start(Stage stage) throws Exception {

        VBox vbox = new VBox(10.0d);

        vbox.getChildren().addAll(
                buildHeader(),
                new Separator(),
                buildErrorMessage(),
                buildMessageContent(),
                new Separator(),
                buildEchoContent()
        );

        vbox.setPadding(new Insets(10.0d));

        Scene scene = new Scene(vbox, 736, 414);

        stage.setScene(scene);
        stage.setTitle("Keycloak FX Demo");
        stage.setOnCloseRequest(e -> Platform.exit());
        stage.show();
    }

    void login(ActionEvent evt) {
        this.errorMessageProperty.set("");
        if( keycloak == null ) {
            return;
        }
        SwingUtilities.invokeLater(() -> {
            try {
                keycloak.loginDesktop();

                long minValidity = 30L;
                this.token = keycloak.getTokenString(minValidity, TimeUnit.SECONDS);

                Platform.runLater(() -> {
                    this.loggedInProperty.set(true);
                    this.nameProperty.set("Welcome " + keycloak.getToken().getName());
                });

            } catch (Exception exc) {
                Platform.runLater( () -> {
                    this.loggedInProperty.set(false);
                    this.errorMessageProperty.set( exc.getClass() + ":" + exc.getMessage());
                });
            }
        });
    }

    void logout(ActionEvent evt) {
        this.errorMessageProperty.set("");
        if( keycloak == null ) {
            return;
        }
        this.loggedInProperty.set(false);
        this.nameProperty.set("");
        SwingUtilities.invokeLater(() -> {
            try {
                keycloak.logout();
                keycloak = null;
            } catch(Exception exc) {
                Platform.runLater( () -> {
                    this.loggedInProperty.set(false);
                    this.errorMessageProperty.set( exc.getClass() + ":" + exc.getMessage());
                }); }
        });
    }

    void fetchMessage(ActionEvent evt) {
        this.errorMessageProperty.set("");
        if( token != null ) {
            System.out.println("token=" + token);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/kc-rest/api/message"))
                    .header("Authorization", "Bearer " + token)
                    .build();
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(messageProperty::set)
                    .join();
        } else {
            this.errorMessageProperty.set("Token Is Not Set");
        }
    }

    void echo(ActionEvent evt) {

        String input = inputProperty.get();;

        Task<String> task = new Task<>() {
            @Override
            protected String call() throws Exception {
                final Hashtable<String, String> jndiProperties = new Hashtable<>();
                jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY,
                        "org.wildfly.naming.client.WildFlyInitialContextFactory");
//                jndiProperties.put(Context.PROVIDER_URL, "remote+http://localhost:8080");
/*
                jndiProperties.put(Context.SECURITY_PRINCIPAL, "carlw");
                jndiProperties.put(Context.SECURITY_CREDENTIALS, "vKcmCqub");
*/
                final Context context = new InitialContext(jndiProperties);
                Echo echoService = (Echo) context.lookup("ejb:/kc/EchoBean!us.bekwam.security.examples.kc.Echo");
                return echoService.echo(input);
            }

            @Override
            protected void succeeded() {
                resultProperty.set(getValue());
            }

            @Override
            protected void failed() {
                KeycloakFX.this.errorMessageProperty.set(getException().getClass().getName() + "; "+ getException().getMessage());
            }
        };

        new Thread(task).start();
    }
}
