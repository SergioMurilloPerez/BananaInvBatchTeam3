package es.netmind.banana_invoices.persistence;

import es.netmind.banana_invoices.models.Recibo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

public class JPAIReciboRepo implements IReciboRepo {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Recibo> findAll() {
        Query query = em.createQuery("SELECT e FROM Recibo e");
        return (List<Recibo>) query.getResultList();
    }

    @Override
    @Transactional
    public Recibo save(Recibo rec) {
        em.persist(rec);
        return rec;
    }

    @Override
    @Transactional
    public Recibo findById(Long id) {
        return em.find(Recibo.class, id);
    }
}
