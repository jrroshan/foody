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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dell
 */
@Entity
@Table(name = "restaurants", catalog = "foody_bag", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Restaurants.findAll", query = "SELECT r FROM Restaurants r")
    , @NamedQuery(name = "Restaurants.findById", query = "SELECT r FROM Restaurants r WHERE r.id = :id")
    , @NamedQuery(name = "Restaurants.findByName", query = "SELECT r FROM Restaurants r WHERE r.name = :name")
    , @NamedQuery(name = "Restaurants.findByAddress", query = "SELECT r FROM Restaurants r WHERE r.address = :address")
    , @NamedQuery(name = "Restaurants.findByContact", query = "SELECT r FROM Restaurants r WHERE r.contact = :contact")
    , @NamedQuery(name = "Restaurants.findByWebsite", query = "SELECT r FROM Restaurants r WHERE r.website = :website")
    , @NamedQuery(name = "Restaurants.findByStatus", query = "SELECT r FROM Restaurants r WHERE r.status = :status")})
public class Restaurants implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "address")
    private String address;
    @Basic(optional = false)
    @Column(name = "contact")
    private long contact;
    @Basic(optional = false)
    @Column(name = "website")
    private String website;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;

    public Restaurants() {
    }

    public Restaurants(Integer id) {
        this.id = id;
    }

    public Restaurants(Integer id, String name, String address, long contact, String website, String status) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.website = website;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getContact() {
        return contact;
    }

    public void setContact(long contact) {
        this.contact = contact;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        if (!(object instanceof Restaurants)) {
            return false;
        }
        Restaurants other = (Restaurants) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.kist.bit.foodybag.entity.Restaurants[ id=" + id + " ]";
    }
    
}
