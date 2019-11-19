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
import edu.kist.bit.foodybag.entity.CustomerOrder;
import java.util.ArrayList;
import java.util.List;
import edu.kist.bit.foodybag.entity.Ratings;
import edu.kist.bit.foodybag.entity.Bills;
import edu.kist.bit.foodybag.entity.Users;
import edu.kist.bit.foodybag.services.exceptions.IllegalOrphanException;
import edu.kist.bit.foodybag.services.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

/**
 *
 * @author Dell
 */
public class UsersJpaController implements Serializable {
    
    private EntityManagerFactory emf = null;

    public UsersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Users users) {
        if (users.getCustomerOrderList() == null) {
            users.setCustomerOrderList(new ArrayList<CustomerOrder>());
        }
        if (users.getRatingsList() == null) {
            users.setRatingsList(new ArrayList<Ratings>());
        }
        if (users.getBillsList() == null) {
            users.setBillsList(new ArrayList<Bills>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<CustomerOrder> attachedCustomerOrderList = new ArrayList<CustomerOrder>();
            for (CustomerOrder customerOrderListCustomerOrderToAttach : users.getCustomerOrderList()) {
                customerOrderListCustomerOrderToAttach = em.getReference(customerOrderListCustomerOrderToAttach.getClass(), customerOrderListCustomerOrderToAttach.getId());
                attachedCustomerOrderList.add(customerOrderListCustomerOrderToAttach);
            }
            users.setCustomerOrderList(attachedCustomerOrderList);
            List<Ratings> attachedRatingsList = new ArrayList<Ratings>();
            for (Ratings ratingsListRatingsToAttach : users.getRatingsList()) {
                ratingsListRatingsToAttach = em.getReference(ratingsListRatingsToAttach.getClass(), ratingsListRatingsToAttach.getId());
                attachedRatingsList.add(ratingsListRatingsToAttach);
            }
            users.setRatingsList(attachedRatingsList);
            List<Bills> attachedBillsList = new ArrayList<Bills>();
            for (Bills billsListBillsToAttach : users.getBillsList()) {
                billsListBillsToAttach = em.getReference(billsListBillsToAttach.getClass(), billsListBillsToAttach.getId());
                attachedBillsList.add(billsListBillsToAttach);
            }
            users.setBillsList(attachedBillsList);
            em.persist(users);
            for (CustomerOrder customerOrderListCustomerOrder : users.getCustomerOrderList()) {
                Users oldUserIdOfCustomerOrderListCustomerOrder = customerOrderListCustomerOrder.getUserId();
                customerOrderListCustomerOrder.setUserId(users);
                customerOrderListCustomerOrder = em.merge(customerOrderListCustomerOrder);
                if (oldUserIdOfCustomerOrderListCustomerOrder != null) {
                    oldUserIdOfCustomerOrderListCustomerOrder.getCustomerOrderList().remove(customerOrderListCustomerOrder);
                    oldUserIdOfCustomerOrderListCustomerOrder = em.merge(oldUserIdOfCustomerOrderListCustomerOrder);
                }
            }
            for (Ratings ratingsListRatings : users.getRatingsList()) {
                Users oldUserIdOfRatingsListRatings = ratingsListRatings.getUserId();
                ratingsListRatings.setUserId(users);
                ratingsListRatings = em.merge(ratingsListRatings);
                if (oldUserIdOfRatingsListRatings != null) {
                    oldUserIdOfRatingsListRatings.getRatingsList().remove(ratingsListRatings);
                    oldUserIdOfRatingsListRatings = em.merge(oldUserIdOfRatingsListRatings);
                }
            }
            for (Bills billsListBills : users.getBillsList()) {
                Users oldUserIdOfBillsListBills = billsListBills.getUserId();
                billsListBills.setUserId(users);
                billsListBills = em.merge(billsListBills);
                if (oldUserIdOfBillsListBills != null) {
                    oldUserIdOfBillsListBills.getBillsList().remove(billsListBills);
                    oldUserIdOfBillsListBills = em.merge(oldUserIdOfBillsListBills);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Users users) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Users persistentUsers = em.find(Users.class, users.getId());
            List<CustomerOrder> customerOrderListOld = persistentUsers.getCustomerOrderList();
            List<CustomerOrder> customerOrderListNew = users.getCustomerOrderList();
            List<Ratings> ratingsListOld = persistentUsers.getRatingsList();
            List<Ratings> ratingsListNew = users.getRatingsList();
            List<Bills> billsListOld = persistentUsers.getBillsList();
            List<Bills> billsListNew = users.getBillsList();
            List<String> illegalOrphanMessages = null;
            for (CustomerOrder customerOrderListOldCustomerOrder : customerOrderListOld) {
                if (!customerOrderListNew.contains(customerOrderListOldCustomerOrder)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CustomerOrder " + customerOrderListOldCustomerOrder + " since its userId field is not nullable.");
                }
            }
            for (Ratings ratingsListOldRatings : ratingsListOld) {
                if (!ratingsListNew.contains(ratingsListOldRatings)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ratings " + ratingsListOldRatings + " since its userId field is not nullable.");
                }
            }
            for (Bills billsListOldBills : billsListOld) {
                if (!billsListNew.contains(billsListOldBills)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Bills " + billsListOldBills + " since its userId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<CustomerOrder> attachedCustomerOrderListNew = new ArrayList<CustomerOrder>();
            for (CustomerOrder customerOrderListNewCustomerOrderToAttach : customerOrderListNew) {
                customerOrderListNewCustomerOrderToAttach = em.getReference(customerOrderListNewCustomerOrderToAttach.getClass(), customerOrderListNewCustomerOrderToAttach.getId());
                attachedCustomerOrderListNew.add(customerOrderListNewCustomerOrderToAttach);
            }
            customerOrderListNew = attachedCustomerOrderListNew;
            users.setCustomerOrderList(customerOrderListNew);
            List<Ratings> attachedRatingsListNew = new ArrayList<Ratings>();
            for (Ratings ratingsListNewRatingsToAttach : ratingsListNew) {
                ratingsListNewRatingsToAttach = em.getReference(ratingsListNewRatingsToAttach.getClass(), ratingsListNewRatingsToAttach.getId());
                attachedRatingsListNew.add(ratingsListNewRatingsToAttach);
            }
            ratingsListNew = attachedRatingsListNew;
            users.setRatingsList(ratingsListNew);
            List<Bills> attachedBillsListNew = new ArrayList<Bills>();
            for (Bills billsListNewBillsToAttach : billsListNew) {
                billsListNewBillsToAttach = em.getReference(billsListNewBillsToAttach.getClass(), billsListNewBillsToAttach.getId());
                attachedBillsListNew.add(billsListNewBillsToAttach);
            }
            billsListNew = attachedBillsListNew;
            users.setBillsList(billsListNew);
            users = em.merge(users);
            for (CustomerOrder customerOrderListNewCustomerOrder : customerOrderListNew) {
                if (!customerOrderListOld.contains(customerOrderListNewCustomerOrder)) {
                    Users oldUserIdOfCustomerOrderListNewCustomerOrder = customerOrderListNewCustomerOrder.getUserId();
                    customerOrderListNewCustomerOrder.setUserId(users);
                    customerOrderListNewCustomerOrder = em.merge(customerOrderListNewCustomerOrder);
                    if (oldUserIdOfCustomerOrderListNewCustomerOrder != null && !oldUserIdOfCustomerOrderListNewCustomerOrder.equals(users)) {
                        oldUserIdOfCustomerOrderListNewCustomerOrder.getCustomerOrderList().remove(customerOrderListNewCustomerOrder);
                        oldUserIdOfCustomerOrderListNewCustomerOrder = em.merge(oldUserIdOfCustomerOrderListNewCustomerOrder);
                    }
                }
            }
            for (Ratings ratingsListNewRatings : ratingsListNew) {
                if (!ratingsListOld.contains(ratingsListNewRatings)) {
                    Users oldUserIdOfRatingsListNewRatings = ratingsListNewRatings.getUserId();
                    ratingsListNewRatings.setUserId(users);
                    ratingsListNewRatings = em.merge(ratingsListNewRatings);
                    if (oldUserIdOfRatingsListNewRatings != null && !oldUserIdOfRatingsListNewRatings.equals(users)) {
                        oldUserIdOfRatingsListNewRatings.getRatingsList().remove(ratingsListNewRatings);
                        oldUserIdOfRatingsListNewRatings = em.merge(oldUserIdOfRatingsListNewRatings);
                    }
                }
            }
            for (Bills billsListNewBills : billsListNew) {
                if (!billsListOld.contains(billsListNewBills)) {
                    Users oldUserIdOfBillsListNewBills = billsListNewBills.getUserId();
                    billsListNewBills.setUserId(users);
                    billsListNewBills = em.merge(billsListNewBills);
                    if (oldUserIdOfBillsListNewBills != null && !oldUserIdOfBillsListNewBills.equals(users)) {
                        oldUserIdOfBillsListNewBills.getBillsList().remove(billsListNewBills);
                        oldUserIdOfBillsListNewBills = em.merge(oldUserIdOfBillsListNewBills);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = users.getId();
                if (findUsers(id) == null) {
                    throw new NonexistentEntityException("The users with id " + id + " no longer exists.");
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
            Users users;
            try {
                users = em.getReference(Users.class, id);
                users.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The users with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<CustomerOrder> customerOrderListOrphanCheck = users.getCustomerOrderList();
            for (CustomerOrder customerOrderListOrphanCheckCustomerOrder : customerOrderListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Users (" + users + ") cannot be destroyed since the CustomerOrder " + customerOrderListOrphanCheckCustomerOrder + " in its customerOrderList field has a non-nullable userId field.");
            }
            List<Ratings> ratingsListOrphanCheck = users.getRatingsList();
            for (Ratings ratingsListOrphanCheckRatings : ratingsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Users (" + users + ") cannot be destroyed since the Ratings " + ratingsListOrphanCheckRatings + " in its ratingsList field has a non-nullable userId field.");
            }
            List<Bills> billsListOrphanCheck = users.getBillsList();
            for (Bills billsListOrphanCheckBills : billsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Users (" + users + ") cannot be destroyed since the Bills " + billsListOrphanCheckBills + " in its billsList field has a non-nullable userId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(users);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Users> findUsersEntities() {
        return findUsersEntities(true, -1, -1);
    }

    public List<Users> findUsersEntities(int maxResults, int firstResult) {
        return findUsersEntities(false, maxResults, firstResult);
    }

    private List<Users> findUsersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Users.class));
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

    public Users findUsers(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Users.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Users> rt = cq.from(Users.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Users checkLogin(String email) throws NonexistentEntityException{
        EntityManager em = getEntityManager();
        Users result = null;
        try {
            result = (Users) em.createNamedQuery("Users.findByEmail")
                .setParameter("email", email)
                .getSingleResult();
        } catch (NullPointerException | NoResultException e) {
            throw new NonexistentEntityException("The users with email: " + email + " no longer exists.");
        }
        return result;
    }
    
}
