package us.bekwam.security.examples.ctx;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class ItemBean {

    @PersistenceContext
    private EntityManager em;

    public List<Item> findAllItems() {
        Query q = em.createQuery("SELECT i FROM Item i ORDER BY i.owner, i.id DESC");
        return q.getResultList();
    }

    public List<Item> findItemsByOwner(String owner) {
        Query q = em.createQuery("SELECT i FROM Item i WHERE i.owner = :owner ORDER BY i.id DESC");
        q.setParameter("owner", owner);
        return q.getResultList();
    }
}
