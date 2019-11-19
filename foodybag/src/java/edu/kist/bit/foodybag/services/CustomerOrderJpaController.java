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
import edu.kist.bit.foodybag.entity.ItemsOrder;
import java.util.ArrayList;
import java.util.List;
import edu.kist.bit.foodybag.entity.Bills;
import edu.kist.bit.foodybag.entity.CustomerOrder;
import edu.kist.bit.foodybag.services.exceptions.IllegalOrphanException;
import edu.kist.bit.foodybag.services.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dell
 */
public class CustomerOrderJpaController implements Serializable {

    public CustomerOrderJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CustomerOrder customerOrder) {
        if (customerOrder.getItemsOrderList() == null) {
            customerOrder.setItemsOrderList(new ArrayList<ItemsOrder>());
        }
        if (customerOrder.getBillsList() == null) {
            customerOrder.setBillsList(new ArrayList<Bills>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Users userId = customerOrder.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getId());
                customerOrder.setUserId(userId);
            }
            List<ItemsOrder> attachedItemsOrderList = new ArrayList<ItemsOrder>();
            for (ItemsOrder itemsOrderListItemsOrderToAttach : customerOrder.getItemsOrderList()) {
                itemsOrderListItemsOrderToAttach = em.getReference(itemsOrderListItemsOrderToAttach.getClass(), itemsOrderListItemsOrderToAttach.getId());
                attachedItemsOrderList.add(itemsOrderListItemsOrderToAttach);
            }
            customerOrder.setItemsOrderList(attachedItemsOrderList);
            List<Bills> attachedBillsList = new ArrayList<Bills>();
            for (Bills billsListBillsToAttach : customerOrder.getBillsList()) {
                billsListBillsToAttach = em.getReference(billsListBillsToAttach.getClass(), billsListBillsToAttach.getId());
                attachedBillsList.add(billsListBillsToAttach);
            }
            customerOrder.setBillsList(attachedBillsList);
            em.persist(customerOrder);
            if (userId != null) {
                userId.getCustomerOrderList().add(customerOrder);
                userId = em.merge(userId);
            }
            for (ItemsOrder itemsOrderListItemsOrder : customerOrder.getItemsOrderList()) {
                CustomerOrder oldOrdersIdOfItemsOrderListItemsOrder = itemsOrderListItemsOrder.getOrdersId();
                itemsOrderListItemsOrder.setOrdersId(customerOrder);
                itemsOrderListItemsOrder = em.merge(itemsOrderListItemsOrder);
                if (oldOrdersIdOfItemsOrderListItemsOrder != null) {
                    oldOrdersIdOfItemsOrderListItemsOrder.getItemsOrderList().remove(itemsOrderListItemsOrder);
                    oldOrdersIdOfItemsOrderListItemsOrder = em.merge(oldOrdersIdOfItemsOrderListItemsOrder);
                }
            }
            for (Bills billsListBills : customerOrder.getBillsList()) {
                CustomerOrder oldOrderIdOfBillsListBills = billsListBills.getOrderId();
                billsListBills.setOrderId(customerOrder);
                billsListBills = em.merge(billsListBills);
                if (oldOrderIdOfBillsListBills != null) {
                    oldOrderIdOfBillsListBills.getBillsList().remove(billsListBills);
                    oldOrderIdOfBillsListBills = em.merge(oldOrderIdOfBillsListBills);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CustomerOrder customerOrder) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CustomerOrder persistentCustomerOrder = em.find(CustomerOrder.class, customerOrder.getId());
            Users userIdOld = persistentCustomerOrder.getUserId();
            Users userIdNew = customerOrder.getUserId();
            List<ItemsOrder> itemsOrderListOld = persistentCustomerOrder.getItemsOrderList();
            List<ItemsOrder> itemsOrderListNew = customerOrder.getItemsOrderList();
            List<Bills> billsListOld = persistentCustomerOrder.getBillsList();
            List<Bills> billsListNew = customerOrder.getBillsList();
            List<String> illegalOrphanMessages = null;
            for (ItemsOrder itemsOrderListOldItemsOrder : itemsOrderListOld) {
                if (!itemsOrderListNew.contains(itemsOrderListOldItemsOrder)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ItemsOrder " + itemsOrderListOldItemsOrder + " since its ordersId field is not nullable.");
                }
            }
            for (Bills billsListOldBills : billsListOld) {
                if (!billsListNew.contains(billsListOldBills)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Bills " + billsListOldBills + " since its orderId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getId());
                customerOrder.setUserId(userIdNew);
            }
            List<ItemsOrder> attachedItemsOrderListNew = new ArrayList<ItemsOrder>();
            for (ItemsOrder itemsOrderListNewItemsOrderToAttach : itemsOrderListNew) {
                itemsOrderListNewItemsOrderToAttach = em.getReference(itemsOrderListNewItemsOrderToAttach.getClass(), itemsOrderListNewItemsOrderToAttach.getId());
                attachedItemsOrderListNew.add(itemsOrderListNewItemsOrderToAttach);
            }
            itemsOrderListNew = attachedItemsOrderListNew;
            customerOrder.setItemsOrderList(itemsOrderListNew);
            List<Bills> attachedBillsListNew = new ArrayList<Bills>();
            for (Bills billsListNewBillsToAttach : billsListNew) {
                billsListNewBillsToAttach = em.getReference(billsListNewBillsToAttach.getClass(), billsListNewBillsToAttach.getId());
                attachedBillsListNew.add(billsListNewBillsToAttach);
            }
            billsListNew = attachedBillsListNew;
            customerOrder.setBillsList(billsListNew);
            customerOrder = em.merge(customerOrder);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getCustomerOrderList().remove(customerOrder);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getCustomerOrderList().add(customerOrder);
                userIdNew = em.merge(userIdNew);
            }
            for (ItemsOrder itemsOrderListNewItemsOrder : itemsOrderListNew) {
                if (!itemsOrderListOld.contains(itemsOrderListNewItemsOrder)) {
                    CustomerOrder oldOrdersIdOfItemsOrderListNewItemsOrder = itemsOrderListNewItemsOrder.getOrdersId();
                    itemsOrderListNewItemsOrder.setOrdersId(customerOrder);
                    itemsOrderListNewItemsOrder = em.merge(itemsOrderListNewItemsOrder);
                    if (oldOrdersIdOfItemsOrderListNewItemsOrder != null && !oldOrdersIdOfItemsOrderListNewItemsOrder.equals(customerOrder)) {
                        oldOrdersIdOfItemsOrderListNewItemsOrder.getItemsOrderList().remove(itemsOrderListNewItemsOrder);
                        oldOrdersIdOfItemsOrderListNewItemsOrder = em.merge(oldOrdersIdOfItemsOrderListNewItemsOrder);
                    }
                }
            }
            for (Bills billsListNewBills : billsListNew) {
                if (!billsListOld.contains(billsListNewBills)) {
                    CustomerOrder oldOrderIdOfBillsListNewBills = billsListNewBills.getOrderId();
                    billsListNewBills.setOrderId(customerOrder);
                    billsListNewBills = em.merge(billsListNewBills);
                    if (oldOrderIdOfBillsListNewBills != null && !oldOrderIdOfBillsListNewBills.equals(customerOrder)) {
                        oldOrderIdOfBillsListNewBills.getBillsList().remove(billsListNewBills);
                        oldOrderIdOfBillsListNewBills = em.merge(oldOrderIdOfBillsListNewBills);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = customerOrder.getId();
                if (findCustomerOrder(id) == null) {
                    throw new NonexistentEntityException("The customerOrder with id " + id + " no longer exists.");
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
            CustomerOrder customerOrder;
            try {
                customerOrder = em.getReference(CustomerOrder.class, id);
                customerOrder.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The customerOrder with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ItemsOrder> itemsOrderListOrphanCheck = customerOrder.getItemsOrderList();
            for (ItemsOrder itemsOrderListOrphanCheckItemsOrder : itemsOrderListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CustomerOrder (" + customerOrder + ") cannot be destroyed since the ItemsOrder " + itemsOrderListOrphanCheckItemsOrder + " in its itemsOrderList field has a non-nullable ordersId field.");
            }
            List<Bills> billsListOrphanCheck = customerOrder.getBillsList();
            for (Bills billsListOrphanCheckBills : billsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CustomerOrder (" + customerOrder + ") cannot be destroyed since the Bills " + billsListOrphanCheckBills + " in its billsList field has a non-nullable orderId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Users userId = customerOrder.getUserId();
            if (userId != null) {
                userId.getCustomerOrderList().remove(customerOrder);
                userId = em.merge(userId);
            }
            em.remove(customerOrder);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CustomerOrder> findCustomerOrderEntities() {
        return findCustomerOrderEntities(true, -1, -1);
    }

    public List<CustomerOrder> findCustomerOrderEntities(int maxResults, int firstResult) {
        return findCustomerOrderEntities(false, maxResults, firstResult);
    }

    private List<CustomerOrder> findCustomerOrderEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CustomerOrder.class));
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

    public CustomerOrder findCustomerOrder(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CustomerOrder.class, id);
        } finally {
            em.close();
        }
    }

    public int getCustomerOrderCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CustomerOrder> rt = cq.from(CustomerOrder.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
