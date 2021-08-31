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

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

import java.io.IOException;

/**
 * @author carl
 */
public class KCGuiceModule extends AbstractModule {

    private final String[] args;
    private final KCRunnerFacade kcRunnerFacade;

    public KCGuiceModule(String[] args) throws IOException, InterruptedException {
        this.args = args;
        this.kcRunnerFacade = new KCRunnerFacade(args);
        this.kcRunnerFacade.init();
    }

    @Override
    protected void configure() {
        bind(String[].class).toInstance(args);
        bindInterceptor(
                Matchers.any(),
                Matchers.annotatedWith(Tokenized.class),
                new TokenizedInterceptor(this.kcRunnerFacade)
        );
    }
}
