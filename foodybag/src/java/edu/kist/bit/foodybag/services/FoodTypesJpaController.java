/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kist.bit.foodybag.services;

import edu.kist.bit.foodybag.entity.FoodTypes;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.kist.bit.foodybag.entity.Foods;
import edu.kist.bit.foodybag.services.exceptions.IllegalOrphanException;
import edu.kist.bit.foodybag.services.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dell
 */
public class FoodTypesJpaController implements Serializable {

    public FoodTypesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FoodTypes foodTypes) {
        if (foodTypes.getFoodsList() == null) {
            foodTypes.setFoodsList(new ArrayList<Foods>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Foods> attachedFoodsList = new ArrayList<Foods>();
            for (Foods foodsListFoodsToAttach : foodTypes.getFoodsList()) {
                foodsListFoodsToAttach = em.getReference(foodsListFoodsToAttach.getClass(), foodsListFoodsToAttach.getId());
                attachedFoodsList.add(foodsListFoodsToAttach);
            }
            foodTypes.setFoodsList(attachedFoodsList);
            em.persist(foodTypes);
            for (Foods foodsListFoods : foodTypes.getFoodsList()) {
                FoodTypes oldTypeIdOfFoodsListFoods = foodsListFoods.getTypeId();
                foodsListFoods.setTypeId(foodTypes);
                foodsListFoods = em.merge(foodsListFoods);
                if (oldTypeIdOfFoodsListFoods != null) {
                    oldTypeIdOfFoodsListFoods.getFoodsList().remove(foodsListFoods);
                    oldTypeIdOfFoodsListFoods = em.merge(oldTypeIdOfFoodsListFoods);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FoodTypes foodTypes) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FoodTypes persistentFoodTypes = em.find(FoodTypes.class, foodTypes.getId());
            List<Foods> foodsListOld = persistentFoodTypes.getFoodsList();
            List<Foods> foodsListNew = foodTypes.getFoodsList();
            List<String> illegalOrphanMessages = null;
            for (Foods foodsListOldFoods : foodsListOld) {
                if (!foodsListNew.contains(foodsListOldFoods)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Foods " + foodsListOldFoods + " since its typeId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Foods> attachedFoodsListNew = new ArrayList<Foods>();
            for (Foods foodsListNewFoodsToAttach : foodsListNew) {
                foodsListNewFoodsToAttach = em.getReference(foodsListNewFoodsToAttach.getClass(), foodsListNewFoodsToAttach.getId());
                attachedFoodsListNew.add(foodsListNewFoodsToAttach);
            }
            foodsListNew = attachedFoodsListNew;
            foodTypes.setFoodsList(foodsListNew);
            foodTypes = em.merge(foodTypes);
            for (Foods foodsListNewFoods : foodsListNew) {
                if (!foodsListOld.contains(foodsListNewFoods)) {
                    FoodTypes oldTypeIdOfFoodsListNewFoods = foodsListNewFoods.getTypeId();
                    foodsListNewFoods.setTypeId(foodTypes);
                    foodsListNewFoods = em.merge(foodsListNewFoods);
                    if (oldTypeIdOfFoodsListNewFoods != null && !oldTypeIdOfFoodsListNewFoods.equals(foodTypes)) {
                        oldTypeIdOfFoodsListNewFoods.getFoodsList().remove(foodsListNewFoods);
                        oldTypeIdOfFoodsListNewFoods = em.merge(oldTypeIdOfFoodsListNewFoods);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = foodTypes.getId();
                if (findFoodTypes(id) == null) {
                    throw new NonexistentEntityException("The foodTypes with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FoodTypes foodTypes;
            try {
                foodTypes = em.getReference(FoodTypes.class, id);
                foodTypes.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The foodTypes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Foods> foodsListOrphanCheck = foodTypes.getFoodsList();
            for (Foods foodsListOrphanCheckFoods : foodsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This FoodTypes (" + foodTypes + ") cannot be destroyed since the Foods " + foodsListOrphanCheckFoods + " in its foodsList field has a non-nullable typeId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(foodTypes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FoodTypes> findFoodTypesEntities() {
        return findFoodTypesEntities(true, -1, -1);
    }

    public List<FoodTypes> findFoodTypesEntities(int maxResults, int firstResult) {
        return findFoodTypesEntities(false, maxResults, firstResult);
    }

    private List<FoodTypes> findFoodTypesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FoodTypes.class));
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

    public FoodTypes findFoodTypes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FoodTypes.class, id);
        } finally {
            em.close();
        }
    }

    public int getFoodTypesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FoodTypes> rt = cq.from(FoodTypes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
