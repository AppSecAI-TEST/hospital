package com.neusoft.hs.portal.framework.security;

import org.springframework.context.ApplicationContext;

import com.neusoft.hs.application.organization.UserAdminAppService;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.platform.bean.ApplicationContextUtil;
import com.neusoft.hs.platform.exception.HsException;

public class UserUtil {

	private static AbstractUser user = null;

	public static AbstractUser getUser() throws HsException {
		if (user == null) {
			throw new HsException("没有登录");
		}

		return user;
	}

	public static AbstractUser getLoginUser() {
		return user;
	}

	public static void setUser(AbstractUser u) {

		ApplicationContext context = ApplicationContextUtil
				.getApplicationContext();

		AbstractUser loginUser = user;
		user = context.getBean(UserAdminAppService.class).find(u.getId());

		if (user != null) {
			context.publishEvent(new LoginEvent(user));
		} else {
			if (loginUser != null) {
				context.publishEvent(new LogoutEvent(loginUser));
			}
		}
	}

}
