package com.neusoft.hs.platform.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.neusoft.hs.platform.bean.ApplicationContextUtil;
import com.neusoft.hs.platform.exception.HsException;

public class DateUtil {

	private static Date sysDate = null;

	public static Date getSysDate() {
		if (sysDate == null) {
			sysDate = new Date();
		}
		return sysDate;
	}

	public static void setSysDate(Date date) {
		if (sysDate == null || !sysDate.equals(date)) {
			Date oldDate = sysDate;
			sysDate = date;
			ApplicationContextUtil.getApplicationContext().publishEvent(
					new SysDateUpdateEvent(oldDate, sysDate));
		}
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

	public static Date createDay(String dateStr, int day) throws HsException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = simpleDateFormat.parse(dateStr);
			if (day > 0) {
				date = addDay(date, day);
			}
			return date;
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

	public static Date createMinute(String dateStr, int day) throws HsException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm");
		try {
			Date date = simpleDateFormat.parse(dateStr);
			if (day > 0) {
				date = addDay(date, day);
			}
			return date;
		} catch (ParseException e) {
			throw new HsException(e);
		}
	}

	public static String toString(Date date) {
		if (date == null) {
			return null;
		}
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

	public static Date reduceHour(Date date, int hour) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.HOUR, -hour);
		return calendar.getTime();
	}

	public static Date addMinute(Date date, int minute) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.MINUTE, minute);
		return calendar.getTime();
	}

	public static Date getSysDateStart() {
		return getDateStart(getSysDate());
	}

	public static Date getDateStart(Date date) {

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	public static int calDay(Date startDate, Date endDate) {
		long dayTime = getDateStart(endDate).getTime()
				- getDateStart(startDate).getTime();
		if (dayTime == 0L) {
			return 1;
		} else {
			return new Long(dayTime / 86400000).intValue();
		}
	}

	public static List<Date> calDayStart(Date startDate, Date endDate) {
		List<Date> dayStarts = new ArrayList<Date>();
		Date dayStart = getDateStart(startDate);
		dayStart = addDay(dayStart, 1);
		while (dayStart.before(endDate)) {
			dayStarts.add(dayStart);
			dayStart = addDay(dayStart, 1);
		}
		return dayStarts;
	}

	/**
	 * 判断指定时间否是在startDate的24小时内
	 * 
	 * @param date
	 * @param startDate
	 * @return
	 */
	public static boolean isDayIn(Date date, Date startDate) {
		long time = date.getTime() - startDate.getTime();
		return time >= 0 && time < 24 * 3600 * 1000;
	}

	public static void main(String[] args) throws HsException {
		DateUtil.setSysDate(DateUtil.createMinute("2016-12-29 13:05"));

		System.out.println(DateUtil.toString(DateUtil.getDateStart(DateUtil
				.getSysDate())));
	}

}
