//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\visit\\VisitDomainService.java

package com.neusoft.hs.domain.visit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.platform.user.User;

@Service
@Transactional(rollbackFor = Exception.class)
public class VisitDomainService 
{
	@Autowired
	private VisitRepo visitRepo;
   
   /**
    * @roseuid 584A6AAC03AB
    */
   public void create(Visit visit, User user) 
   {
	   
	   visitRepo.save(visit);
   }
   
   /**
    * @roseuid 584E03140020
    */
   public void find() 
   {
    
   }
   
   /**
    * @roseuid 584E135F0389
    */
   public void intoWard() 
   {
    
   }
   
   /**
    * @roseuid 5852564401AC
    */
   public void leaveWard() 
   {
    
   }
}
