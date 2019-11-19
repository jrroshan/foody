package edu.kist.bit.foodybag.entity;

import edu.kist.bit.foodybag.entity.CustomerOrder;
import edu.kist.bit.foodybag.entity.Foods;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-17T08:35:47")
@StaticMetamodel(ItemsOrder.class)
public class ItemsOrder_ { 

    public static volatile SingularAttribute<ItemsOrder, Foods> foodsId;
    public static volatile SingularAttribute<ItemsOrder, Double> amount;
    public static volatile SingularAttribute<ItemsOrder, CustomerOrder> ordersId;
    public static volatile SingularAttribute<ItemsOrder, Integer> quantity;
    public static volatile SingularAttribute<ItemsOrder, Integer> id;

}