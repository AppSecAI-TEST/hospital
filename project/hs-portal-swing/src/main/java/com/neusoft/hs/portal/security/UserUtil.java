package com.neusoft.hs.portal.security;

import com.neusoft.hs.domain.order.OrderDeletedEvent;
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
		user = u;

		if (user == null) {
			ApplicationContextUtil.getApplicationContext().publishEvent(
					new LogoutEvent(user));
		} else {
			ApplicationContextUtil.getApplicationContext().publishEvent(
					new LoginEvent(user));
		}
	}

}
