/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kist.bit.foodybag.services;

import edu.kist.bit.foodybag.entity.Bills;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.kist.bit.foodybag.entity.Users;
import edu.kist.bit.foodybag.entity.CustomerOrder;
import edu.kist.bit.foodybag.services.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dell
 */
public class BillsJpaController implements Serializable {

    public BillsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bills bills) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Users userId = bills.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getId());
                bills.setUserId(userId);
            }
            CustomerOrder orderId = bills.getOrderId();
            if (orderId != null) {
                orderId = em.getReference(orderId.getClass(), orderId.getId());
                bills.setOrderId(orderId);
            }
            em.persist(bills);
            if (userId != null) {
                userId.getBillsList().add(bills);
                userId = em.merge(userId);
            }
            if (orderId != null) {
                orderId.getBillsList().add(bills);
                orderId = em.merge(orderId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Bills bills) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bills persistentBills = em.find(Bills.class, bills.getId());
            Users userIdOld = persistentBills.getUserId();
            Users userIdNew = bills.getUserId();
            CustomerOrder orderIdOld = persistentBills.getOrderId();
            CustomerOrder orderIdNew = bills.getOrderId();
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getId());
                bills.setUserId(userIdNew);
            }
            if (orderIdNew != null) {
                orderIdNew = em.getReference(orderIdNew.getClass(), orderIdNew.getId());
                bills.setOrderId(orderIdNew);
            }
            bills = em.merge(bills);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getBillsList().remove(bills);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getBillsList().add(bills);
                userIdNew = em.merge(userIdNew);
            }
            if (orderIdOld != null && !orderIdOld.equals(orderIdNew)) {
                orderIdOld.getBillsList().remove(bills);
                orderIdOld = em.merge(orderIdOld);
            }
            if (orderIdNew != null && !orderIdNew.equals(orderIdOld)) {
                orderIdNew.getBillsList().add(bills);
                orderIdNew = em.merge(orderIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bills.getId();
                if (findBills(id) == null) {
                    throw new NonexistentEntityException("The bills with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bills bills;
            try {
                bills = em.getReference(Bills.class, id);
                bills.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bills with id " + id + " no longer exists.", enfe);
            }
            Users userId = bills.getUserId();
            if (userId != null) {
                userId.getBillsList().remove(bills);
                userId = em.merge(userId);
            }
            CustomerOrder orderId = bills.getOrderId();
            if (orderId != null) {
                orderId.getBillsList().remove(bills);
                orderId = em.merge(orderId);
            }
            em.remove(bills);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Bills> findBillsEntities() {
        return findBillsEntities(true, -1, -1);
    }

    public List<Bills> findBillsEntities(int maxResults, int firstResult) {
        return findBillsEntities(false, maxResults, firstResult);
    }

    private List<Bills> findBillsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bills.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Bills findBills(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bills.class, id);
        } finally {
            em.close();
        }
    }

    public int getBillsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bills> rt = cq.from(Bills.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
