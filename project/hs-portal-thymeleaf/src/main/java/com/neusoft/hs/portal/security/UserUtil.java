package com.neusoft.hs.portal.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.user.User;

public class UserUtil {
	
	private static User user = null;

	public static User getUser() throws HsException {

		if(user == null){
			Subject subject = SecurityUtils.getSubject();
			User user = (User)subject.getPrincipal();
			if(user == null){
				throw new HsException("没有登录");
			}
			
			return user;
		}

		return user;
	}
	
	public static void setUser(User u){
		user = u;
	}

}
