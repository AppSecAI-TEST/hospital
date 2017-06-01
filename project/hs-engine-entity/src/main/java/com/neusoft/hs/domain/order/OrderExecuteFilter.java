//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\engine\\entity\\order\\OrderExecuteFilter.java

package com.neusoft.hs.domain.order;

import java.util.Map;

import com.neusoft.hs.domain.organization.AbstractUser;


public interface OrderExecuteFilter 
{
   
   /**
    * @return DesignModel.DesignElement.domain.order.OrderExecute
    * @roseuid 592E649801E1
    */
   public OrderExecuteFilterCondition createCondition(Map<String, Object> params, AbstractUser user);
}
