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
import edu.kist.bit.foodybag.entity.FoodTypes;
import edu.kist.bit.foodybag.entity.Foods;
import edu.kist.bit.foodybag.entity.ItemsOrder;
import java.util.ArrayList;
import java.util.List;
import edu.kist.bit.foodybag.entity.Ratings;
import edu.kist.bit.foodybag.services.exceptions.IllegalOrphanException;
import edu.kist.bit.foodybag.services.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dell
 */
public class FoodsJpaController implements Serializable {

    public FoodsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Foods foods) {
        if (foods.getItemsOrderList() == null) {
            foods.setItemsOrderList(new ArrayList<ItemsOrder>());
        }
        if (foods.getRatingsList() == null) {
            foods.setRatingsList(new ArrayList<Ratings>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FoodTypes typeId = foods.getTypeId();
            if (typeId != null) {
                typeId = em.getReference(typeId.getClass(), typeId.getId());
                foods.setTypeId(typeId);
            }
            List<ItemsOrder> attachedItemsOrderList = new ArrayList<ItemsOrder>();
            for (ItemsOrder itemsOrderListItemsOrderToAttach : foods.getItemsOrderList()) {
                itemsOrderListItemsOrderToAttach = em.getReference(itemsOrderListItemsOrderToAttach.getClass(), itemsOrderListItemsOrderToAttach.getId());
                attachedItemsOrderList.add(itemsOrderListItemsOrderToAttach);
            }
            foods.setItemsOrderList(attachedItemsOrderList);
            List<Ratings> attachedRatingsList = new ArrayList<Ratings>();
            for (Ratings ratingsListRatingsToAttach : foods.getRatingsList()) {
                ratingsListRatingsToAttach = em.getReference(ratingsListRatingsToAttach.getClass(), ratingsListRatingsToAttach.getId());
                attachedRatingsList.add(ratingsListRatingsToAttach);
            }
            foods.setRatingsList(attachedRatingsList);
            em.persist(foods);
            if (typeId != null) {
                typeId.getFoodsList().add(foods);
                typeId = em.merge(typeId);
            }
            for (ItemsOrder itemsOrderListItemsOrder : foods.getItemsOrderList()) {
                Foods oldFoodsIdOfItemsOrderListItemsOrder = itemsOrderListItemsOrder.getFoodsId();
                itemsOrderListItemsOrder.setFoodsId(foods);
                itemsOrderListItemsOrder = em.merge(itemsOrderListItemsOrder);
                if (oldFoodsIdOfItemsOrderListItemsOrder != null) {
                    oldFoodsIdOfItemsOrderListItemsOrder.getItemsOrderList().remove(itemsOrderListItemsOrder);
                    oldFoodsIdOfItemsOrderListItemsOrder = em.merge(oldFoodsIdOfItemsOrderListItemsOrder);
                }
            }
            for (Ratings ratingsListRatings : foods.getRatingsList()) {
                Foods oldFoodIdOfRatingsListRatings = ratingsListRatings.getFoodId();
                ratingsListRatings.setFoodId(foods);
                ratingsListRatings = em.merge(ratingsListRatings);
                if (oldFoodIdOfRatingsListRatings != null) {
                    oldFoodIdOfRatingsListRatings.getRatingsList().remove(ratingsListRatings);
                    oldFoodIdOfRatingsListRatings = em.merge(oldFoodIdOfRatingsListRatings);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Foods foods) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Foods persistentFoods = em.find(Foods.class, foods.getId());
            FoodTypes typeIdOld = persistentFoods.getTypeId();
            FoodTypes typeIdNew = foods.getTypeId();
            List<ItemsOrder> itemsOrderListOld = persistentFoods.getItemsOrderList();
            List<ItemsOrder> itemsOrderListNew = foods.getItemsOrderList();
            List<Ratings> ratingsListOld = persistentFoods.getRatingsList();
            List<Ratings> ratingsListNew = foods.getRatingsList();
            List<String> illegalOrphanMessages = null;
            for (ItemsOrder itemsOrderListOldItemsOrder : itemsOrderListOld) {
                if (!itemsOrderListNew.contains(itemsOrderListOldItemsOrder)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ItemsOrder " + itemsOrderListOldItemsOrder + " since its foodsId field is not nullable.");
                }
            }
            for (Ratings ratingsListOldRatings : ratingsListOld) {
                if (!ratingsListNew.contains(ratingsListOldRatings)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ratings " + ratingsListOldRatings + " since its foodId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (typeIdNew != null) {
                typeIdNew = em.getReference(typeIdNew.getClass(), typeIdNew.getId());
                foods.setTypeId(typeIdNew);
            }
            List<ItemsOrder> attachedItemsOrderListNew = new ArrayList<ItemsOrder>();
            for (ItemsOrder itemsOrderListNewItemsOrderToAttach : itemsOrderListNew) {
                itemsOrderListNewItemsOrderToAttach = em.getReference(itemsOrderListNewItemsOrderToAttach.getClass(), itemsOrderListNewItemsOrderToAttach.getId());
                attachedItemsOrderListNew.add(itemsOrderListNewItemsOrderToAttach);
            }
            itemsOrderListNew = attachedItemsOrderListNew;
            foods.setItemsOrderList(itemsOrderListNew);
            List<Ratings> attachedRatingsListNew = new ArrayList<Ratings>();
            for (Ratings ratingsListNewRatingsToAttach : ratingsListNew) {
                ratingsListNewRatingsToAttach = em.getReference(ratingsListNewRatingsToAttach.getClass(), ratingsListNewRatingsToAttach.getId());
                attachedRatingsListNew.add(ratingsListNewRatingsToAttach);
            }
            ratingsListNew = attachedRatingsListNew;
            foods.setRatingsList(ratingsListNew);
            foods = em.merge(foods);
            if (typeIdOld != null && !typeIdOld.equals(typeIdNew)) {
                typeIdOld.getFoodsList().remove(foods);
                typeIdOld = em.merge(typeIdOld);
            }
            if (typeIdNew != null && !typeIdNew.equals(typeIdOld)) {
                typeIdNew.getFoodsList().add(foods);
                typeIdNew = em.merge(typeIdNew);
            }
            for (ItemsOrder itemsOrderListNewItemsOrder : itemsOrderListNew) {
                if (!itemsOrderListOld.contains(itemsOrderListNewItemsOrder)) {
                    Foods oldFoodsIdOfItemsOrderListNewItemsOrder = itemsOrderListNewItemsOrder.getFoodsId();
                    itemsOrderListNewItemsOrder.setFoodsId(foods);
                    itemsOrderListNewItemsOrder = em.merge(itemsOrderListNewItemsOrder);
                    if (oldFoodsIdOfItemsOrderListNewItemsOrder != null && !oldFoodsIdOfItemsOrderListNewItemsOrder.equals(foods)) {
                        oldFoodsIdOfItemsOrderListNewItemsOrder.getItemsOrderList().remove(itemsOrderListNewItemsOrder);
                        oldFoodsIdOfItemsOrderListNewItemsOrder = em.merge(oldFoodsIdOfItemsOrderListNewItemsOrder);
                    }
                }
            }
            for (Ratings ratingsListNewRatings : ratingsListNew) {
                if (!ratingsListOld.contains(ratingsListNewRatings)) {
                    Foods oldFoodIdOfRatingsListNewRatings = ratingsListNewRatings.getFoodId();
                    ratingsListNewRatings.setFoodId(foods);
                    ratingsListNewRatings = em.merge(ratingsListNewRatings);
                    if (oldFoodIdOfRatingsListNewRatings != null && !oldFoodIdOfRatingsListNewRatings.equals(foods)) {
                        oldFoodIdOfRatingsListNewRatings.getRatingsList().remove(ratingsListNewRatings);
                        oldFoodIdOfRatingsListNewRatings = em.merge(oldFoodIdOfRatingsListNewRatings);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = foods.getId();
                if (findFoods(id) == null) {
                    throw new NonexistentEntityException("The foods with id " + id + " no longer exists.");
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
            Foods foods;
            try {
                foods = em.getReference(Foods.class, id);
                foods.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The foods with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ItemsOrder> itemsOrderListOrphanCheck = foods.getItemsOrderList();
            for (ItemsOrder itemsOrderListOrphanCheckItemsOrder : itemsOrderListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Foods (" + foods + ") cannot be destroyed since the ItemsOrder " + itemsOrderListOrphanCheckItemsOrder + " in its itemsOrderList field has a non-nullable foodsId field.");
            }
            List<Ratings> ratingsListOrphanCheck = foods.getRatingsList();
            for (Ratings ratingsListOrphanCheckRatings : ratingsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Foods (" + foods + ") cannot be destroyed since the Ratings " + ratingsListOrphanCheckRatings + " in its ratingsList field has a non-nullable foodId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            FoodTypes typeId = foods.getTypeId();
            if (typeId != null) {
                typeId.getFoodsList().remove(foods);
                typeId = em.merge(typeId);
            }
            em.remove(foods);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Foods> findFoodsEntities() {
        return findFoodsEntities(true, -1, -1);
    }

    public List<Foods> findFoodsEntities(int maxResults, int firstResult) {
        return findFoodsEntities(false, maxResults, firstResult);
    }

    private List<Foods> findFoodsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Foods.class));
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

    public Foods findFoods(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Foods.class, id);
        } finally {
            em.close();
        }
    }

    public int getFoodsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Foods> rt = cq.from(Foods.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
