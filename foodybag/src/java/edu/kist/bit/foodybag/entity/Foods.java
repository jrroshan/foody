/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kist.bit.foodybag.entity;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Dell
 */
@Entity
@Table(name = "foods", catalog = "foody_bag", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Foods.findAll", query = "SELECT f FROM Foods f")
    , @NamedQuery(name = "Foods.findById", query = "SELECT f FROM Foods f WHERE f.id = :id")
    , @NamedQuery(name = "Foods.findByName", query = "SELECT f FROM Foods f WHERE f.name = :name")
    , @NamedQuery(name = "Foods.findBySize", query = "SELECT f FROM Foods f WHERE f.size = :size")
    , @NamedQuery(name = "Foods.findByPrice", query = "SELECT f FROM Foods f WHERE f.price = :price")
    , @NamedQuery(name = "Foods.findByImg", query = "SELECT f FROM Foods f WHERE f.img = :img")})
public class Foods implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "size")
    private String size;
    @Basic(optional = false)
    @Column(name = "price")
    private long price;
    @Basic(optional = false)
    @Column(name = "img")
    private String img;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "foodsId")
    private List<ItemsOrder> itemsOrderList;
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private FoodTypes typeId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "foodId")
    private List<Ratings> ratingsList;

    public Foods() {
    }

    public Foods(Integer id) {
        this.id = id;
    }

    public Foods(Integer id, String name, long price, String img) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.img = img;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @XmlTransient
    public List<ItemsOrder> getItemsOrderList() {
        return itemsOrderList;
    }

    public void setItemsOrderList(List<ItemsOrder> itemsOrderList) {
        this.itemsOrderList = itemsOrderList;
    }

    public FoodTypes getTypeId() {
        return typeId;
    }

    public void setTypeId(FoodTypes typeId) {
        this.typeId = typeId;
    }

    @XmlTransient
    public List<Ratings> getRatingsList() {
        return ratingsList;
    }

    public void setRatingsList(List<Ratings> ratingsList) {
        this.ratingsList = ratingsList;
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
        if (!(object instanceof Foods)) {
            return false;
        }
        Foods other = (Foods) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.kist.bit.foodybag.entity.Foods[ id=" + id + " ]";
    }
    
}
