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
package us.bekwam.security.examples.kc.desktop.capitalize;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.bekwam.security.examples.kc.commons.ejb.Capitalize;
import us.bekwam.security.examples.kc.desktop.framework.Tokenized;

/**
 * @author carl
 */
public class CapitalizeKCDecorator implements Capitalize {

    private Logger log = LoggerFactory.getLogger(CapitalizeKCDecorator.class);

    private Capitalize ejb;

    public void init(Capitalize ejb) {
        this.ejb = ejb;
    }

    @Override
    @Tokenized
    public String capitalize(String input) {
        return ejb.capitalize(input);
    }

    @Override
    @Tokenized
    public String allCaps(String in) {
        return ejb.allCaps(in);
    }
}
