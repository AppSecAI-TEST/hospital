package com.neusoft.hs.platform.log;

import org.slf4j.LoggerFactory;

import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

public class LogUtil {

	public static void log(Class c, String format, Object... arguments) {

		Object[] args = new Object[arguments.length + 1];
		try {
			args[0] = DateUtil.toString(DateUtil.getSysDate());
		} catch (HsException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < arguments.length; i++) {
			args[i + 1] = arguments[i];
		}

		LoggerFactory.getLogger(c).info("{} " + format, args);
	}

}
