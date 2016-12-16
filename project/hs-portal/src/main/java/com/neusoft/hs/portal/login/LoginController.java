//Source file: E:\\my_workspace\\workprojects\\201605��Ʒ�з�\\project\\gp-portal\\src\\main\\java\\com\\rofine\\gp\\portal\\DesignModel\\DesignElement\\portal\\organization\\target\\execute\\ExecuteController.java

package com.neusoft.hs.portal.login;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neusoft.hs.application.user.User2Impl;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.security.UserUtil;

@Controller
public class LoginController {
	
	private Logger logger = Logger.getLogger(LoginController.class);

	private static final String LOGIN_PAGE = "account/login";
	private static final String UNAUTHORIZED_PAGE = "account/unauthorized";

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {
		return LOGIN_PAGE;
	}
	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String fail1(Model model) {
		model.addAttribute("msg", "登录失败！");
		return LOGIN_PAGE;
	}

	@RequestMapping(value = "login/success", method = RequestMethod.GET)
	public String success() {
		return "redirect:/index";
	}
	
	@RequestMapping(value = "unauthorized", method = RequestMethod.GET)
	public String unauthorized(Model model) {
		model.addAttribute("msg", "您不能访问该资源！");
		return UNAUTHORIZED_PAGE;
	}

	@RequestMapping(value = "login/user/{userId}/org/{orgId}/dept/{deptId}/role/{roleIds}", method = RequestMethod.GET)
	public String login(@PathVariable String userId, @PathVariable String orgId, @PathVariable String deptId, @PathVariable String roleIds, Model model) {
		
		model.addAttribute("userId", userId);
		model.addAttribute("orgId", orgId);
		model.addAttribute("deptId", deptId);
		model.addAttribute("roleIds", roleIds);
		
		return "login";
	}

	@RequestMapping(value = "login/user/{userId}/org/{orgId}/dept/{deptId}/role/{roleIds}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(@ModelAttribute("user") User2Impl user) throws HsException {

		UserUtil.setUser(user);
	
		Map<String, Object> rtn = new HashMap<String, Object>();

		rtn.put("code", "1");
		rtn.put("msg", "操作成功");

		return rtn;
	}

	
}
