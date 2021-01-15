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

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author carl
 */
@Stateless
public class RegistrationBean {

    @PersistenceContext
    EntityManager em;

    public Long create(Registration r) {
        r.setRegisteredOn(ZonedDateTime.now());
        em.persist(r);
        return r.getId();
    }

    public List<Registration> findAll() {
        TypedQuery<Registration> r =
                em.createQuery(
                        "SELECT r FROM Registration r ORDER BY r.registeredOn DESC",
                        Registration.class
                );
        return r.getResultList();
    }
}
