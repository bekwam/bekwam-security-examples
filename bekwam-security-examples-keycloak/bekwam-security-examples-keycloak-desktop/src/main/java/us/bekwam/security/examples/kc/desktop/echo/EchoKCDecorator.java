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
package us.bekwam.security.examples.kc.desktop.echo;

import us.bekwam.security.examples.kc.commons.ejb.Echo;
import us.bekwam.security.examples.kc.desktop.framework.Tokenized;

/**
 * @author carl
 */
public class EchoKCDecorator implements Echo {

    private Echo ejb; // bean to wrap

    public void init(Echo ejb) {
        this.ejb = ejb;
    }

    @Override
    @Tokenized
    public String echo(String input) {
        return this.ejb.echo(input);
    }
}
