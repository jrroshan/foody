/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kist.bit.foodybag.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dell
 */
@Entity
@Table(name = "items_order", catalog = "foody_bag", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ItemsOrder.findAll", query = "SELECT i FROM ItemsOrder i")
    , @NamedQuery(name = "ItemsOrder.findById", query = "SELECT i FROM ItemsOrder i WHERE i.id = :id")
    , @NamedQuery(name = "ItemsOrder.findByQuantity", query = "SELECT i FROM ItemsOrder i WHERE i.quantity = :quantity")
    , @NamedQuery(name = "ItemsOrder.findByAmount", query = "SELECT i FROM ItemsOrder i WHERE i.amount = :amount")})
public class ItemsOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "quantity")
    private int quantity;
    @Basic(optional = false)
    @Column(name = "amount")
    private double amount;
    @JoinColumn(name = "foods_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Foods foodsId;
    @JoinColumn(name = "orders_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CustomerOrder ordersId;

    public ItemsOrder() {
    }

    public ItemsOrder(Integer id) {
        this.id = id;
    }

    public ItemsOrder(Integer id, int quantity, double amount) {
        this.id = id;
        this.quantity = quantity;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Foods getFoodsId() {
        return foodsId;
    }

    public void setFoodsId(Foods foodsId) {
        this.foodsId = foodsId;
    }

    public CustomerOrder getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(CustomerOrder ordersId) {
        this.ordersId = ordersId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemsOrder)) {
            return false;
        }
        ItemsOrder other = (ItemsOrder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.kist.bit.foodybag.entity.ItemsOrder[ id=" + id + " ]";
    }
    
}
