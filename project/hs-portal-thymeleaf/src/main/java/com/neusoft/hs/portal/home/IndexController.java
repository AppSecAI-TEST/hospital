package com.neusoft.hs.portal.home;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.security.UserUtil;

@Controller
@RequestMapping("")
public class IndexController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index1(){
		return "redirect:/index";
	}
	
	@RequiresPermissions("user:view")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model) throws HsException{
		
		model.addAttribute("user", UserUtil.getUser());
		
		return "index";
	}

}
