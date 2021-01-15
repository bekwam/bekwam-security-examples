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
package us.bekwam.security.examples.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.*;

/**
 * @author carl
 */
@MessageDriven(activationConfig =  {
        @ActivationConfigProperty(propertyName = "destination",
                propertyValue = "java:/jms/queue/SecexInbound"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode",
                propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "javax.jms.Queue")
})
public class RegistrationMessageBean implements MessageListener {

    private Logger log = LoggerFactory.getLogger(RegistrationMessageBean.class);

    @Inject
    RegistrationBean registrationBean;

    @Override
    public void onMessage(Message message) {
        if( message instanceof ObjectMessage ){
            try {
                Registration r = (Registration) ((ObjectMessage) message).getObject();
                Long id = registrationBean.create(r);
                if( log.isDebugEnabled() ) {
                    log.debug("[ON MESSAGE] create id={}", id);
                }
            } catch(JMSException exc) {
                log.error("error retrieving object from message", exc);
            }
        } else if( message instanceof TextMessage) {
            try {
                String s = ((TextMessage) message).getText();
                Long id = registrationBean.create(new Registration(s));
                if( log.isDebugEnabled() ) {
                    log.debug("[ON MESSAGE] create id={}", id);
                }
            } catch(JMSException exc) {
                log.error("error retrieving text from message", exc);
            }
        } else {
            if( message == null ) {
                log.error("message is null");
            } else {
                log.error("unrecognized Message type {}", message.getClass().getName());
            }
        }
    }
}
