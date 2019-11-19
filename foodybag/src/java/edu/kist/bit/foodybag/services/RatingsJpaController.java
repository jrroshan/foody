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
import edu.kist.bit.foodybag.entity.Users;
import edu.kist.bit.foodybag.entity.Foods;
import edu.kist.bit.foodybag.entity.Ratings;
import edu.kist.bit.foodybag.services.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dell
 */
public class RatingsJpaController implements Serializable {

    public RatingsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ratings ratings) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Users userId = ratings.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getId());
                ratings.setUserId(userId);
            }
            Foods foodId = ratings.getFoodId();
            if (foodId != null) {
                foodId = em.getReference(foodId.getClass(), foodId.getId());
                ratings.setFoodId(foodId);
            }
            em.persist(ratings);
            if (userId != null) {
                userId.getRatingsList().add(ratings);
                userId = em.merge(userId);
            }
            if (foodId != null) {
                foodId.getRatingsList().add(ratings);
                foodId = em.merge(foodId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ratings ratings) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ratings persistentRatings = em.find(Ratings.class, ratings.getId());
            Users userIdOld = persistentRatings.getUserId();
            Users userIdNew = ratings.getUserId();
            Foods foodIdOld = persistentRatings.getFoodId();
            Foods foodIdNew = ratings.getFoodId();
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getId());
                ratings.setUserId(userIdNew);
            }
            if (foodIdNew != null) {
                foodIdNew = em.getReference(foodIdNew.getClass(), foodIdNew.getId());
                ratings.setFoodId(foodIdNew);
            }
            ratings = em.merge(ratings);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getRatingsList().remove(ratings);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getRatingsList().add(ratings);
                userIdNew = em.merge(userIdNew);
            }
            if (foodIdOld != null && !foodIdOld.equals(foodIdNew)) {
                foodIdOld.getRatingsList().remove(ratings);
                foodIdOld = em.merge(foodIdOld);
            }
            if (foodIdNew != null && !foodIdNew.equals(foodIdOld)) {
                foodIdNew.getRatingsList().add(ratings);
                foodIdNew = em.merge(foodIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ratings.getId();
                if (findRatings(id) == null) {
                    throw new NonexistentEntityException("The ratings with id " + id + " no longer exists.");
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
            Ratings ratings;
            try {
                ratings = em.getReference(Ratings.class, id);
                ratings.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ratings with id " + id + " no longer exists.", enfe);
            }
            Users userId = ratings.getUserId();
            if (userId != null) {
                userId.getRatingsList().remove(ratings);
                userId = em.merge(userId);
            }
            Foods foodId = ratings.getFoodId();
            if (foodId != null) {
                foodId.getRatingsList().remove(ratings);
                foodId = em.merge(foodId);
            }
            em.remove(ratings);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ratings> findRatingsEntities() {
        return findRatingsEntities(true, -1, -1);
    }

    public List<Ratings> findRatingsEntities(int maxResults, int firstResult) {
        return findRatingsEntities(false, maxResults, firstResult);
    }

    private List<Ratings> findRatingsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ratings.class));
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

    public Ratings findRatings(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ratings.class, id);
        } finally {
            em.close();
        }
    }

    public int getRatingsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ratings> rt = cq.from(Ratings.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
