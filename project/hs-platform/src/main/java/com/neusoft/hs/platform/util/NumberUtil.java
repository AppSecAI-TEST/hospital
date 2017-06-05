package com.neusoft.hs.platform.util;

import java.util.Random;

public class NumberUtil {

	public static int random(int n) {
		return new Random().nextInt(n);
	}

}
