//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\visit\\Visit.java

package com.neusoft.hs.domain.visit;

import java.util.Date;

import com.neusoft.hs.domain.cost.ChargeBill;
import com.neusoft.hs.domain.cost.VisitChargeItem;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordClip;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.Nurse;

public class Visit 
{
   private int name;
   private int state;
   private int stateDesc;
   private int bed;
   private int intoWardDate;
   private Date leaveWardDate;
   public Nurse respNurse;
   public VisitLog logs[];
   public MedicalRecordClip medicalRecordClip;
   public Order orders[];
   public VisitChargeItem visitChargeItem[];
   public OrderExecute executes[];
   public ChargeBill chargeBill;
   public Doctor respDoctor;
   public Dept respDept;
   
   /**
    * @roseuid 58573EC602EB
    */
   public Visit() 
   {
    
   }
   
   /**
    * @roseuid 584E0FAD00A9
    */
   public void setState() 
   {
    
   }
   
   /**
    * @roseuid 584E13DB03E1
    */
   public void setNurse() 
   {
    
   }
   
   /**
    * @roseuid 584E14180159
    */
   public void setBed() 
   {
    
   }
   
   /**
    * @roseuid 584E17B0019F
    */
   public void setIntoWardDate() 
   {
    
   }
   
   /**
    * @roseuid 5852526403A5
    */
   public void intoWard() 
   {
    
   }
   
   /**
    * @roseuid 585252D80085
    */
   public void save() 
   {
    
   }
   
   /**
    * @roseuid 58525F0D0273
    */
   public void leaveWard() 
   {
    
   }
   
   /**
    * @roseuid 58525F4D0122
    */
   public void setLeaveWardDate() 
   {
    
   }
   
   /**
    * @roseuid 58537EBF0298
    */
   public void setRespDept() 
   {
    
   }
   
   /**
    * @roseuid 58537EE0008F
    */
   public void setDoctor() 
   {
    
   }
   
   /**
    * @roseuid 585381610380
    */
   public void setChargeBill() 
   {
    
   }
   
   /**
    * @roseuid 585394AD004B
    */
   public void addVisitChargeItem() 
   {
    
   }
}
