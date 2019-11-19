package edu.kist.bit.foodybag.entity;

import edu.kist.bit.foodybag.entity.FoodTypes;
import edu.kist.bit.foodybag.entity.ItemsOrder;
import edu.kist.bit.foodybag.entity.Ratings;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-17T08:35:47")
@StaticMetamodel(Foods.class)
public class Foods_ { 

    public static volatile SingularAttribute<Foods, String> img;
    public static volatile SingularAttribute<Foods, String> size;
    public static volatile SingularAttribute<Foods, Long> price;
    public static volatile ListAttribute<Foods, Ratings> ratingsList;
    public static volatile SingularAttribute<Foods, String> name;
    public static volatile SingularAttribute<Foods, FoodTypes> typeId;
    public static volatile SingularAttribute<Foods, Integer> id;
    public static volatile ListAttribute<Foods, ItemsOrder> itemsOrderList;

}