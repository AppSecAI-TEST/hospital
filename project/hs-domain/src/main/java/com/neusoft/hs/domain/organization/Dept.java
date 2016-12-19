//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\organization\\Dept.java

package com.neusoft.hs.domain.organization;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.cost.ChargeRecord;
import com.neusoft.hs.domain.visit.Visit;

public class Dept extends Unit 
{
   public Nurse nurse;
   public OrderExecute theOrderExecute[];
   public ChargeRecord chargeRecord[];
   public Doctor doctor;
   public Visit visits[];
   
   /**
    * @roseuid 58573EC6023B
    */
   public Dept() 
   {
    
   }
}
