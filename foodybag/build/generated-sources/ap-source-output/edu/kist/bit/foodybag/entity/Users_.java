package edu.kist.bit.foodybag.entity;

import edu.kist.bit.foodybag.entity.Bills;
import edu.kist.bit.foodybag.entity.CustomerOrder;
import edu.kist.bit.foodybag.entity.Ratings;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-17T08:35:47")
@StaticMetamodel(Users.class)
public class Users_ { 

    public static volatile SingularAttribute<Users, String> lastName;
    public static volatile SingularAttribute<Users, String> firstName;
    public static volatile SingularAttribute<Users, String> password;
    public static volatile SingularAttribute<Users, String> address;
    public static volatile SingularAttribute<Users, String> role;
    public static volatile ListAttribute<Users, Ratings> ratingsList;
    public static volatile SingularAttribute<Users, Long> contact;
    public static volatile ListAttribute<Users, Bills> billsList;
    public static volatile ListAttribute<Users, CustomerOrder> customerOrderList;
    public static volatile SingularAttribute<Users, Integer> id;
    public static volatile SingularAttribute<Users, String> email;
    public static volatile SingularAttribute<Users, String> status;

}