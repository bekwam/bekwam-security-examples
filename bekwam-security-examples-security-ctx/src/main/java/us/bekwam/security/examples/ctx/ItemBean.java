package us.bekwam.security.examples.ctx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
@RolesAllowed("user")
public class ItemBean {

    private Logger log = LoggerFactory.getLogger(ItemBean.class);

    @PersistenceContext
    private EntityManager em;

    @Resource
    private EJBContext context;

    @RolesAllowed("admin")
    public List<Item> findAllItems() {
        Query q = em.createQuery("SELECT i FROM Item i ORDER BY i.owner, i.id DESC");
        return q.getResultList();
    }

    public List<Item> findItemsByOwner(String owner) {

        if(log.isDebugEnabled()) {
            log.debug("[FIND ITEMS BY OWNER] owner={}", owner);
            log.debug("[FIND ITEMS BY OWNER] principal={}", context.getCallerPrincipal().getName());
            log.debug("[FIND ITEMS BY OWNER] in admin?={}", context.isCallerInRole("admin"));
        }

        if( context.isCallerInRole("admin") ) {
            return this.findAllItems();
        }

        Query q = em.createQuery("SELECT i FROM Item i WHERE i.owner = :owner ORDER BY i.id DESC");
        q.setParameter("owner", owner);
        return q.getResultList();
    }
}
