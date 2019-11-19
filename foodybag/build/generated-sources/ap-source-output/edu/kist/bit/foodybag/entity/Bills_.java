package edu.kist.bit.foodybag.entity;

import edu.kist.bit.foodybag.entity.CustomerOrder;
import edu.kist.bit.foodybag.entity.Users;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-17T08:35:47")
@StaticMetamodel(Bills.class)
public class Bills_ { 

    public static volatile SingularAttribute<Bills, Long> totalAmount;
    public static volatile SingularAttribute<Bills, CustomerOrder> orderId;
    public static volatile SingularAttribute<Bills, Integer> id;
    public static volatile SingularAttribute<Bills, Users> userId;

}