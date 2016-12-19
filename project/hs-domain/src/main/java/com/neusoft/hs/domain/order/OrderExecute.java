//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\OrderExecute.java

package com.neusoft.hs.domain.order;

import java.util.Date;

import com.neusoft.hs.domain.cost.ChargeRecord;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Role;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.user.User;

public class OrderExecute 
{
   private int state;
   private int chargeState;
   private int costState;
   private Date planStartDate;
   private Date planEndDate;
   private Date startDate;
   private Date endDate;
   public Role executeRole;
   public User actualExecutor;
   public Order theOrder;
   public ChargeRecord chargeRecord[];
   public Dept executeDept;
   public Visit visit;
   
   /**
    * @roseuid 58573EC6009B
    */
   public OrderExecute() 
   {
    
   }
   
   /**
    * @roseuid 584F62150363
    */
   public void setState() 
   {
    
   }
   
   /**
    * @roseuid 584F624D0233
    */
   public void send() 
   {
    
   }
   
   /**
    * @roseuid 584F62CB0254
    */
   public void save() 
   {
    
   }
   
   /**
    * @roseuid 584FB6EB03E5
    */
   public void finish() 
   {
    
   }
   
   /**
    * @roseuid 584FB716018C
    */
   private void setEndDate() 
   {
    
   }
   
   /**
    * @roseuid 584FB84E0224
    */
   private void setActualExecutor() 
   {
    
   }
   
   /**
    * @roseuid 58509B990022
    */
   public void createChargeRecords() 
   {
    
   }
   
   /**
    * @roseuid 5850B0720293
    */
   public void setChargeState() 
   {
    
   }
   
   /**
    * @roseuid 5850B08602BB
    */
   public void setCostState() 
   {
    
   }
   
   /**
    * @roseuid 5850B1970103
    */
   public void cancel() 
   {
    
   }
}
