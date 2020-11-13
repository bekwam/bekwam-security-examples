package us.bekwam.security.examples.ctx;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Startup
@Singleton
public class StartupBean {

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void init() {
        em.persist(new Item("abc", "carlw") );
        em.persist(new Item("def", "carlw") );
        em.persist(new Item("123", "jane") );
        em.persist(new Item("XYZ", "elmer"));
    }
}
