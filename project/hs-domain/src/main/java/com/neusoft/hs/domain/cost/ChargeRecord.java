//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\cost\\ChargeRecord.java

package com.neusoft.hs.domain.cost;

import java.util.Date;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.organization.Dept;

public class ChargeRecord 
{
   private float amount;
   private int count;
   private float price;
   private Date createDate;
   public CostRecord theCostRecord;
   public ChargeItem theChargeItem;
   public ChargeRecord newRecord;
   public ChargeRecord original;
   public OrderExecute theOrderExecute;
   public ChargeBill theChargeBill;
   public Dept chargeDept;
   
   /**
    * @roseuid 58574B970259
    */
   public ChargeRecord() 
   {
    
   }
   
   /**
    * @roseuid 5850A1CD019F
    */
   public void createCostRecord() 
   {
    
   }
   
   /**
    * @roseuid 5850BE360360
    */
   public void undo() 
   {
    
   }
}
