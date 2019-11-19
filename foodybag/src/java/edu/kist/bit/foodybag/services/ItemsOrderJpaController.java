/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kist.bit.foodybag.services;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.kist.bit.foodybag.entity.Foods;
import edu.kist.bit.foodybag.entity.CustomerOrder;
import edu.kist.bit.foodybag.entity.ItemsOrder;
import edu.kist.bit.foodybag.services.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dell
 */
public class ItemsOrderJpaController implements Serializable {

    public ItemsOrderJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ItemsOrder itemsOrder) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Foods foodsId = itemsOrder.getFoodsId();
            if (foodsId != null) {
                foodsId = em.getReference(foodsId.getClass(), foodsId.getId());
                itemsOrder.setFoodsId(foodsId);
            }
            CustomerOrder ordersId = itemsOrder.getOrdersId();
            if (ordersId != null) {
                ordersId = em.getReference(ordersId.getClass(), ordersId.getId());
                itemsOrder.setOrdersId(ordersId);
            }
            em.persist(itemsOrder);
            if (foodsId != null) {
                foodsId.getItemsOrderList().add(itemsOrder);
                foodsId = em.merge(foodsId);
            }
            if (ordersId != null) {
                ordersId.getItemsOrderList().add(itemsOrder);
                ordersId = em.merge(ordersId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ItemsOrder itemsOrder) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ItemsOrder persistentItemsOrder = em.find(ItemsOrder.class, itemsOrder.getId());
            Foods foodsIdOld = persistentItemsOrder.getFoodsId();
            Foods foodsIdNew = itemsOrder.getFoodsId();
            CustomerOrder ordersIdOld = persistentItemsOrder.getOrdersId();
            CustomerOrder ordersIdNew = itemsOrder.getOrdersId();
            if (foodsIdNew != null) {
                foodsIdNew = em.getReference(foodsIdNew.getClass(), foodsIdNew.getId());
                itemsOrder.setFoodsId(foodsIdNew);
            }
            if (ordersIdNew != null) {
                ordersIdNew = em.getReference(ordersIdNew.getClass(), ordersIdNew.getId());
                itemsOrder.setOrdersId(ordersIdNew);
            }
            itemsOrder = em.merge(itemsOrder);
            if (foodsIdOld != null && !foodsIdOld.equals(foodsIdNew)) {
                foodsIdOld.getItemsOrderList().remove(itemsOrder);
                foodsIdOld = em.merge(foodsIdOld);
            }
            if (foodsIdNew != null && !foodsIdNew.equals(foodsIdOld)) {
                foodsIdNew.getItemsOrderList().add(itemsOrder);
                foodsIdNew = em.merge(foodsIdNew);
            }
            if (ordersIdOld != null && !ordersIdOld.equals(ordersIdNew)) {
                ordersIdOld.getItemsOrderList().remove(itemsOrder);
                ordersIdOld = em.merge(ordersIdOld);
            }
            if (ordersIdNew != null && !ordersIdNew.equals(ordersIdOld)) {
                ordersIdNew.getItemsOrderList().add(itemsOrder);
                ordersIdNew = em.merge(ordersIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = itemsOrder.getId();
                if (findItemsOrder(id) == null) {
                    throw new NonexistentEntityException("The itemsOrder with id " + id + " no longer exists.");
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
            ItemsOrder itemsOrder;
            try {
                itemsOrder = em.getReference(ItemsOrder.class, id);
                itemsOrder.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The itemsOrder with id " + id + " no longer exists.", enfe);
            }
            Foods foodsId = itemsOrder.getFoodsId();
            if (foodsId != null) {
                foodsId.getItemsOrderList().remove(itemsOrder);
                foodsId = em.merge(foodsId);
            }
            CustomerOrder ordersId = itemsOrder.getOrdersId();
            if (ordersId != null) {
                ordersId.getItemsOrderList().remove(itemsOrder);
                ordersId = em.merge(ordersId);
            }
            em.remove(itemsOrder);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ItemsOrder> findItemsOrderEntities() {
        return findItemsOrderEntities(true, -1, -1);
    }

    public List<ItemsOrder> findItemsOrderEntities(int maxResults, int firstResult) {
        return findItemsOrderEntities(false, maxResults, firstResult);
    }

    private List<ItemsOrder> findItemsOrderEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ItemsOrder.class));
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

    public ItemsOrder findItemsOrder(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ItemsOrder.class, id);
        } finally {
            em.close();
        }
    }

    public int getItemsOrderCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ItemsOrder> rt = cq.from(ItemsOrder.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
