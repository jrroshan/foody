package edu.kist.bit.foodybag.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-17T08:35:47")
@StaticMetamodel(Messages.class)
public class Messages_ { 

    public static volatile SingularAttribute<Messages, Integer> senderId;
    public static volatile SingularAttribute<Messages, Boolean> deleted;
    public static volatile SingularAttribute<Messages, Integer> id;
    public static volatile SingularAttribute<Messages, String> text;
    public static volatile SingularAttribute<Messages, Date> time;
    public static volatile SingularAttribute<Messages, String> status;

}