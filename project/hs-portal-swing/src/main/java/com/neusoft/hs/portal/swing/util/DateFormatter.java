package com.neusoft.hs.portal.swing.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

	public static String formatDate(Date date) {
		if (date == null) {
			return null;
		} else {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			return formatter.format(date);
		}
	}

}
