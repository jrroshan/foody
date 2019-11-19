package edu.kist.bit.foodybag.entity;

import edu.kist.bit.foodybag.entity.Foods;
import edu.kist.bit.foodybag.entity.Users;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-17T08:35:47")
@StaticMetamodel(Ratings.class)
public class Ratings_ { 

    public static volatile SingularAttribute<Ratings, Foods> foodId;
    public static volatile SingularAttribute<Ratings, String> rating;
    public static volatile SingularAttribute<Ratings, Integer> id;
    public static volatile SingularAttribute<Ratings, Users> userId;

}