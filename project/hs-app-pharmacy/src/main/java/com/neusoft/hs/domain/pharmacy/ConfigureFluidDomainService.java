//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\app\\pharmacy\\domain\\ConfigureFluidDomainService.java

package com.neusoft.hs.domain.pharmacy;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.organization.AbstractUser;

@Service
@Transactional(rollbackFor = Exception.class)
public class ConfigureFluidDomainService 
{
   
   /**
    * @roseuid 592E870E03C4
    */
   public ConfigureFluidDomainService() 
   {
    
   }
   
   /**
    * @param executes
    * @roseuid 592E6DFF034D
    */
   public void createOrder(List<OrderExecute> executes, AbstractUser user) 
   {
    
   }
}
