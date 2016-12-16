package com.neusoft.hs.portal.security;

import java.util.ArrayList;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.neusoft.hs.application.user.UserService;
import com.neusoft.hs.platform.user.User;

public class HsRealm extends IniRealm {
	
	@Autowired
	private UserService userService;

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {

		AuthenticationInfo info = super.doGetAuthenticationInfo(token);
		if(info == null){
			return null;
		}

		String userId = (String) token.getPrincipal();

		User user = userService.getUser(userId);
		
		SimpleAccount simpleAccount = (SimpleAccount)info;
		user.setRoleIds(new ArrayList<String>(simpleAccount.getRoles()));
		
		SimplePrincipalCollection principals = (SimplePrincipalCollection)info.getPrincipals();
		principals.clear();
		principals.add(user, "userId");
		
		return info;
	}
}
