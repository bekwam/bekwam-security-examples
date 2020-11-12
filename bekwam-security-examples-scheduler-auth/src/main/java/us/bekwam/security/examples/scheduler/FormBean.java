package us.bekwam.security.examples.scheduler;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
@RolesAllowed({"user","sys"})
public class FormBean {

    @PersistenceContext
    private EntityManager em;

    public void create(Form form) {
        em.persist(form);
    }

    public void update(Form form) {}

    public List<Form> findAllForms() {
        Query q = em.createQuery("SELECT f FROM Form f ORDER BY f.processedOn DESC", Form.class);
        return q.getResultList();
    }

    public List<Form> findReceivedForms() {
        Query q = em.createQuery("SELECT f FROM Form f WHERE f.formStateType = 'RECEIVED'", Form.class);
        return q.getResultList();
    }

    public void receiveForm(Form form) {
        form.setFormStateType(FormStateType.RECEIVED);
        form.setReceivedOn(LocalDateTime.now());
        create(form);
    }

    public void processForms() {
        findReceivedForms()
                .stream()
                .forEach( f -> {
                    f.setFormStateType(FormStateType.PROCESSED);
                    f.setProcessedOn(LocalDateTime.now());
                    update(f);
                });
    }
}
