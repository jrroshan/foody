/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kist.bit.foodybag.services;

import edu.kist.bit.foodybag.entity.Restaurants;
import edu.kist.bit.foodybag.services.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Dell
 */
public class RestaurantsJpaController implements Serializable {

    public RestaurantsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Restaurants restaurants) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(restaurants);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Restaurants restaurants) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            restaurants = em.merge(restaurants);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = restaurants.getId();
                if (findRestaurants(id) == null) {
                    throw new NonexistentEntityException("The restaurants with id " + id + " no longer exists.");
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
            Restaurants restaurants;
            try {
                restaurants = em.getReference(Restaurants.class, id);
                restaurants.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The restaurants with id " + id + " no longer exists.", enfe);
            }
            em.remove(restaurants);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Restaurants> findRestaurantsEntities() {
        return findRestaurantsEntities(true, -1, -1);
    }

    public List<Restaurants> findRestaurantsEntities(int maxResults, int firstResult) {
        return findRestaurantsEntities(false, maxResults, firstResult);
    }

    private List<Restaurants> findRestaurantsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Restaurants.class));
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

    public Restaurants findRestaurants(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Restaurants.class, id);
        } finally {
            em.close();
        }
    }

    public int getRestaurantsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Restaurants> rt = cq.from(Restaurants.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
