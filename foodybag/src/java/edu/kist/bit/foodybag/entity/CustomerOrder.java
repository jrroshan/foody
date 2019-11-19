/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kist.bit.foodybag.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Dell
 */
@Entity
@Table(name = "customer_order", catalog = "foody_bag", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CustomerOrder.findAll", query = "SELECT c FROM CustomerOrder c")
    , @NamedQuery(name = "CustomerOrder.findById", query = "SELECT c FROM CustomerOrder c WHERE c.id = :id")
    , @NamedQuery(name = "CustomerOrder.findByTime", query = "SELECT c FROM CustomerOrder c WHERE c.time = :time")
    , @NamedQuery(name = "CustomerOrder.findByLocation", query = "SELECT c FROM CustomerOrder c WHERE c.location = :location")
    , @NamedQuery(name = "CustomerOrder.findByStatus", query = "SELECT c FROM CustomerOrder c WHERE c.status = :status")})
public class CustomerOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    @Basic(optional = false)
    @Column(name = "location")
    private String location;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ordersId")
    private List<ItemsOrder> itemsOrderList;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users userId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderId")
    private List<Bills> billsList;

    public CustomerOrder() {
    }

    public CustomerOrder(Integer id) {
        this.id = id;
    }

    public CustomerOrder(Integer id, Date time, String location, String status) {
        this.id = id;
        this.time = time;
        this.location = location;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    public List<ItemsOrder> getItemsOrderList() {
        return itemsOrderList;
    }

    public void setItemsOrderList(List<ItemsOrder> itemsOrderList) {
        this.itemsOrderList = itemsOrderList;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    @XmlTransient
    public List<Bills> getBillsList() {
        return billsList;
    }

    public void setBillsList(List<Bills> billsList) {
        this.billsList = billsList;
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
        if (!(object instanceof CustomerOrder)) {
            return false;
        }
        CustomerOrder other = (CustomerOrder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.kist.bit.foodybag.entity.CustomerOrder[ id=" + id + " ]";
    }
    
}
