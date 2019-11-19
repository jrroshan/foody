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
@Table(name = "ratings", catalog = "foody_bag", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ratings.findAll", query = "SELECT r FROM Ratings r")
    , @NamedQuery(name = "Ratings.findById", query = "SELECT r FROM Ratings r WHERE r.id = :id")
    , @NamedQuery(name = "Ratings.findByRating", query = "SELECT r FROM Ratings r WHERE r.rating = :rating")})
public class Ratings implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "rating")
    private String rating;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users userId;
    @JoinColumn(name = "food_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Foods foodId;

    public Ratings() {
    }

    public Ratings(Integer id) {
        this.id = id;
    }

    public Ratings(Integer id, String rating) {
        this.id = id;
        this.rating = rating;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public Foods getFoodId() {
        return foodId;
    }

    public void setFoodId(Foods foodId) {
        this.foodId = foodId;
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
        if (!(object instanceof Ratings)) {
            return false;
        }
        Ratings other = (Ratings) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.kist.bit.foodybag.entity.Ratings[ id=" + id + " ]";
    }
    
}
