package com.neusoft.hs.platform.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.neusoft.hs.platform.exception.HsException;

public class DateUtil {

	private static Date sysDate = null;

	public static Date getSysDate() {
		if (sysDate == null) {
			sysDate = new Date();
		}
		return sysDate;
	}

	public static void setSysDate(Date sysDate1) {
		sysDate = sysDate1;
	}

	public static void clearSysDate() {
		sysDate = null;
	}

	public static int getSysYear() {
		Date sysDate = getSysDate();
		Calendar a = Calendar.getInstance();
		a.setTime(sysDate);
		return a.get(Calendar.YEAR);
	}

	public static Date createDay(String dateStr) throws HsException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return simpleDateFormat.parse(dateStr);
		} catch (ParseException e) {
			throw new HsException(e);
		}
	}

	public static Date createMinute(String dateStr) throws HsException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm");
		try {
			return simpleDateFormat.parse(dateStr);
		} catch (ParseException e) {
			throw new HsException(e);
		}
	}

	public static String toString(Date date) throws HsException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm");
		return simpleDateFormat.format(date);
	}

	public static Date addDay(Date date, int day) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, day);
		return calendar.getTime();
	}

	public static Date addHour(Date date, int hour) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.HOUR, hour);
		return calendar.getTime();
	}

	public static Date addMinute(Date date, int minute) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.MINUTE, minute);
		return calendar.getTime();
	}

	public static Date getSysDateStart() {

		Date sysDate = getSysDate();

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(sysDate);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}
}
