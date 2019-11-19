package edu.kist.bit.foodybag.entity;

import edu.kist.bit.foodybag.entity.Bills;
import edu.kist.bit.foodybag.entity.ItemsOrder;
import edu.kist.bit.foodybag.entity.Users;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-17T08:35:47")
@StaticMetamodel(CustomerOrder.class)
public class CustomerOrder_ { 

    public static volatile ListAttribute<CustomerOrder, Bills> billsList;
    public static volatile SingularAttribute<CustomerOrder, String> location;
    public static volatile SingularAttribute<CustomerOrder, Integer> id;
    public static volatile SingularAttribute<CustomerOrder, Date> time;
    public static volatile SingularAttribute<CustomerOrder, Users> userId;
    public static volatile ListAttribute<CustomerOrder, ItemsOrder> itemsOrderList;
    public static volatile SingularAttribute<CustomerOrder, String> status;

}