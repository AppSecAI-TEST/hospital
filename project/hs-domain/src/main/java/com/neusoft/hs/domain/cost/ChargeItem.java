//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\cost\\ChargeItem.java

package com.neusoft.hs.domain.cost;

import com.neusoft.hs.domain.pharmacy.DrugType;

public class ChargeItem 
{
   private int code;
   private float price;
   private int chargingMode;
   public DrugType drugType;
   public ChargeRecord chargeRecord;
   public VisitChargeItem visitChargeItem[];
   
   /**
    * @roseuid 58573EC500AB
    */
   public ChargeItem() 
   {
    
   }
}
