package br.com.zone.controle.financeiro.daos;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.zone.controle.financeiro.entidades.BaseEntity;
import java.io.Serializable;

public class DAOGenerico<T> implements Serializable {

    private final Class<T> entityClass;

    public DAOGenerico(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void salvar(T t) {
        EntityManager em = EMF.getInstance().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(t);
            em.getTransaction().commit();
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            em.getTransaction().rollback();
        }
        em.close();
    }

    public void excluir(BaseEntity entity) throws Exception {
        EntityManager em = EMF.getInstance().createEntityManager();
        try {
            em.getTransaction().begin();
            T entityToBeRemoved = em.find(entityClass, entity.getId());
            em.remove(entityToBeRemoved);
            em.getTransaction().commit();
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            em.getTransaction().rollback();
            throw ex;
        }
        em.close();
    }

    public void atualizar(T t) {
        EntityManager em = EMF.getInstance().createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(t);
            em.getTransaction().commit();
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            em.getTransaction().rollback();
        }
        em.close();
    }

    public T buscarPorId(Object id) {
        EntityManager em = EMF.getInstance().createEntityManager();
        T resultado = em.find(entityClass, id);
        em.close();
        return resultado;
    }
    
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<T> listarTodos() {
        EntityManager em = EMF.getInstance().createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);
        cq.orderBy(cb.asc(root.get("id")));
        cq.select(root);
        List<T> resultado = em.createQuery(cq).getResultList();
        em.close();
        return resultado;
    }
    
    protected List<T> listar(String namedQuery, Object... params) {
        EntityManager em = EMF.getInstance().createEntityManager();
        Query q = em.createNamedQuery(namedQuery);
        for(int i = 0; i < params.length; i++){
            q.setParameter(i + 1, params[i]);
        }
        List<T> resultado = q.getResultList();
        em.close();
        return resultado;
    }

    @SuppressWarnings("unchecked")
    protected T buscarUmResultado(String namedQuery, Object... params) {
        EntityManager em = EMF.getInstance().createEntityManager();
        T result = null;
        try {
            Query q = em.createNamedQuery(namedQuery);
            for (int i = 0; i < params.length; i++) {
                q.setParameter(i + 1, params[i]);
            }
            result = (T) q.getSingleResult();
        } catch (Exception ex) {
        }
        em.close();
        return result;
    }

}
