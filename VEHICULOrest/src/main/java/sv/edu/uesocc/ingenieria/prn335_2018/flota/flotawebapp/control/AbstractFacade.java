package sv.edu.uesocc.ingenieria.prn335_2018.flota.flotawebapp.control;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/**
 *
 * @author cristian
 * @param <T>
 */
public abstract class AbstractFacade<T> {

    private final Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        EntityManager em = getEntityManager();
        try {
            if (entity != null && em != null) {
                em.persist(entity);
            } else {
                System.out.println("ALGO ES NULO");
            }

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }

    }

    public void edit(T entity) {
        EntityManager em = getEntityManager();
        try {
            if (em != null && entity != null) {
                em.merge(entity);
            } else {
                System.out.println("ALGO ES NULO");
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void remove(T entity) {
        EntityManager em = getEntityManager();
        try {
            if (em != null && entity != null) {
                em.remove(em.merge(entity));
            } else {
                System.out.println("ALGO ES NULO");
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public T find(Object id) {
        EntityManager em = getEntityManager();
        try {
            if (em != null && id != null) {
                return em.find(entityClass, id);
            } else {
                System.out.println("ALGO ES NULO");
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
    }

    public List<T> findAll() {
        EntityManager em = getEntityManager();
        try {
            if (em != null) {
                javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
                cq.select(cq.from(entityClass));
                return em.createQuery(cq).getResultList();
            } else {
                System.out.println("ALGO ES NULO");
                return Collections.emptyList();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return Collections.emptyList();
        }

    }

    public List<T> findRange(int desde, int cuantosReg) {
        EntityManager em = getEntityManager();
        try {
            if (em != null && String.valueOf(desde) != null && String.valueOf(cuantosReg) != null) {
                javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
                cq.select(cq.from(entityClass));
                javax.persistence.Query q = em.createQuery(cq);
                q.setMaxResults(cuantosReg);
                q.setFirstResult(desde);
                return q.getResultList();
            } else {
                System.out.println("ALGO ES NULO");
                return Collections.emptyList();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return Collections.emptyList();
        }

    }

    public int count() {
        EntityManager em = getEntityManager();
        try {
            if (em != null) {
                javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
                javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
                cq.select(em.getCriteriaBuilder().count(rt));
                javax.persistence.Query q = em.createQuery(cq);
                return ((Long) q.getSingleResult()).intValue();
            } else {
                System.out.println("ALGO ES NULO");
                return 0;
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return 0;
        }

    }

}
