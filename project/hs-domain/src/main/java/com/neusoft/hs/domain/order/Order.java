//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\Order.java

package com.neusoft.hs.domain.order;

import java.util.Date;

import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.visit.Visit;

public abstract class Order 
{
   private int state;
   private int stateDesc;
   private Date planStartDate;
   private int count;
   public OrderType type;
   public OrderExecute theOrderExecute[];
   public Doctor creator;
   public Visit visit;
   
   /**
    * @roseuid 58573EC5031B
    */
   public Order() 
   {
    
   }
   
   /**
    * @roseuid 584E6696009D
    */
   public void check() 
   {
    
   }
   
   /**
    * @roseuid 584E6791028E
    */
   public void getCount() 
   {
    
   }
   
   /**
    * @roseuid 584F494100C2
    */
   public void resolve() 
   {
    
   }
   
   /**
    * @roseuid 584F5A920055
    */
   public void addExecutes() 
   {
    
   }
   
   /**
    * @roseuid 584F5B580185
    */
   public void setState() 
   {
    
   }
   
   /**
    * @roseuid 584F5C1E019C
    */
   public void save() 
   {
    
   }
   
   /**
    * @roseuid 584F62A0037C
    */
   public void setStateDesc() 
   {
    
   }
   
   /**
    * @roseuid 5850AF1E016C
    */
   public void cancel() 
   {
    
   }
}
