//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\engine\\entity\\order\\OrderExecuteFilter.java

package com.neusoft.hs.domain.order;

import java.util.List;
import java.util.Map;

import com.neusoft.hs.domain.organization.AbstractUser;


public interface OrderExecuteFilter 
{
   
   /**
    * @return DesignModel.DesignElement.domain.order.OrderExecute
    * @roseuid 592E649801E1
    */
   public List<OrderExecute> filter(Map<String, Object> params, AbstractUser user);
}
