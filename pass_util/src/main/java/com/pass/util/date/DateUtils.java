package com.pass.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.chrono.IsoChronology;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.*;

/**
 * 时间工具类
 * 
 * @Author yuanzhonglin
 * @since 2019/5/27
 */
public class DateUtils {
	/**
	 * 一天的毫秒总数
	 */
	public static final long DAY_IN_MILLIS = 24L * 3600 * 1000;
	
	public static final String STANDARD_DATE_FORMAT = "yyyy-MM-dd";

	public static final String STANDARD_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final String WHOLE_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";


	/**
	 * 获取当前日期和时间
	 *
	 * @author yuanzhonglin
	 * @since 2018/7/26
	 */
	public static Date getTodayTime() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * 获取当前日期，不包括时间
	 *
	 * @author yuanzhonglin
	 * @since 2018/7/26
	 */
	public static Date getToday() {
		Calendar cal = Calendar.getInstance();
		// cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		Date today = cal.getTime();

		return today;
	}

	/**
	 * 将带有时分秒的Date对象截去时间(只保留日期部分)
	 *
	 * @author yuanzhonglin
	 * @since 2018/8/7
	 */
	public static Date truncateTime(Date dateTime) {
		if (dateTime == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(dateTime);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		Date date = cal.getTime();

		return date;
	}


	/**
	 * 判断某个字符串是否为有效的日期
	 * 
	 * @param dateString 时间的字符串，例如：2016-07-22
	 * @param format 字符串匹配模式，例如：yyyy-MM-dd
	 * @author yuanzhonglin
	 * @since Apr 9, 2017
	 */
	public static boolean isDate(String dateString, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		df.setLenient(false);// 将2017-13-01看做是一个非法的日期字符串

		try {
			df.parse(dateString);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * 将字符串转成时间
	 * 
	 * @param dateString 时间的字符串，例如：2016-07-22
	 * @param format 字符串匹配模式，例如：yyyy-MM-dd
	 * @return 返回dateString对应的Date对象
	 * @author yuanzhonglin
	 * @since Jul 22, 2016
	 */
	public static Date stringToDate(String dateString, String format) {
		if (dateString == null || "".equals(dateString)) {
			throw new RuntimeException("传入参数中的[时间串]不能为空！");
		}
		if (format == null || "".equals(format)) {
			throw new RuntimeException("传入参数中的[时间格式]不能为空！");
		}

		SimpleDateFormat df = new SimpleDateFormat(format);
		df.setLenient(false);// 将2017-13-01看做是一个非法的日期字符串
		
		Date date;

		try {
			date = df.parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage());
		}

		return date;
	}

	/**
	 * 将时间转成字符串
	 * 
	 * @param date 时间
	 * @param format 字符串匹配模式，例如：yyyy-MM-dd
	 * @author yuanzhonglin
	 * @since Jul 22, 2016
	 */
	public static String dateToString(Date date, String format) {
		if (date == null) {
			return null;
		}
		if (format == null || "".equals(format)) {
			throw new RuntimeException("传入参数中的[时间格式]不能为空！");
		}

		SimpleDateFormat df = new SimpleDateFormat(format);
		df.setLenient(false);// 将2017-13-01看做是一个非法的日期字符串
		
		String dateString = df.format(date);

		return dateString;
	}

	/**
	 * 将UTC时间字符串转成时间
	 *
	 * @param dateString 时间的字符串，例如：2016-07-22
	 * @param format 字符串匹配模式，例如：yyyy-MM-dd
	 * @return 返回dateString对应的Date对象
	 * @author yuanzhonglin
	 * @since 2018-05-14
	 */
	public static Date utcStringToDate(String dateString, String format) {
		if (dateString == null || "".equals(dateString)) {
			throw new RuntimeException("传入参数中的[时间串]不能为空！");
		}
		if (format == null || "".equals(format)) {
			throw new RuntimeException("传入参数中的[时间格式]不能为空！");
		}

		SimpleDateFormat df = new SimpleDateFormat(format);
		df.setLenient(false);// 将2017-13-01看做是一个非法的日期字符串
		df.setTimeZone(TimeZone.getTimeZone("UTC"));

		Date date;

		try {
			date = df.parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage());
		}

		return date;
	}

	/**
	 * 将传入的Date型参数增加指定的天数
	 * 
	 * @param date 日期
	 * @param dayNumber 增加的天数，可以是负数
	 */
	public static Date addDay(Date date, int dayNumber) {
		if (date == null || dayNumber == 0) {
			return date;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, dayNumber);
		date = cal.getTime();

		return date;
	}

	/**
	 * 将传入的Date型参数增加指定的月数
	 * 
	 * @param date 日期
	 * @param monthNumber 增加的月数，可以是负数
	 */
	public static Date addMonth(Date date, int monthNumber) {
		if (date == null || monthNumber == 0) {
			return date;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, monthNumber);
		date = cal.getTime();

		return date;
	}

	/**
	 * 将传入的Date型参数增加指定的年数
	 *
	 * @param date 日期
	 * @param yearNumber 增加的年数，可以是负数
	 * @author yuanzhonglin
	 * @since Jul 12, 2018
	 */
	public static Date addYear(Date date, int yearNumber) {
		if (date == null || yearNumber == 0) {
			return date;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, yearNumber);
		date = cal.getTime();

		return date;
	}

	/**
	 * 求两个日期之间相差的天数
	 * 
	 * @param beginDate 被减数
	 * @param endDate 减数
	 * @return 返回endDate-beginDate(减数-被减数)的结果，小数部分直接舍去
	 * @author yuanzhonglin
	 * @since Jul 22, 2016
	 */
	public static long getDayDifference(Date beginDate, Date endDate) {
		if (beginDate == null) {
			throw new RuntimeException("传入参数[开始时间]为空");
		}
		if (endDate == null) {
			throw new RuntimeException("传入参数[结束时间]为空");
		}
		long beginMilliseconds = beginDate.getTime();
		long endMilliseconds = endDate.getTime();
		long days = (long) ((endMilliseconds - beginMilliseconds) / DAY_IN_MILLIS);

		return days;
	}

	/**
	 * 求两个日期之间相差的月数
	 * 
	 * @param beginDate 被减数
	 * @param endDate 减数
	 * @return 返回值小数部分直接舍去
	 * @author yuanzhonglin
	 * @since Aug 21, 2016
	 */
	public static long getMonthDifference(Date beginDate, Date endDate) {
		if (beginDate == null) {
			throw new RuntimeException("传入参数[开始时间]为空");
		}
		if (endDate == null) {
			throw new RuntimeException("传入参数[结束时间]为空");
		}

		// yyyy-MM-dd HH:mm:ss
		int year1 = Integer.parseInt(dateToString(beginDate, "yyyy"));
		int year2 = Integer.parseInt(dateToString(endDate, "yyyy"));

		int month1 = Integer.parseInt(dateToString(beginDate, "MM"));
		int month2 = Integer.parseInt(dateToString(endDate, "MM"));

		int day1 = Integer.parseInt(dateToString(beginDate, "dd"));
		int day2 = Integer.parseInt(dateToString(endDate, "dd"));

		double months = (year2 - year1) * 12 + month2 - month1;

		if (day1 == day2) {
			// do nothing
		} else if (day1 == getLastDayIntegerOfMonth(beginDate)
				&& day2 == getLastDayIntegerOfMonth(endDate)) {
			// do nothing
		} else {
			months += (day2 - day1) / 31.00;
		}

		return (int) months;
	}

	/**
	 * 是否为闰年
	 * <p>
	 * (1)非整百年能被4整除的为闰年。（如2004年就是闰年,2010年不是闰年）<br>
	 * (2)能被400整除的是闰年。(如2000年是闰年，1900年不是闰年)<br>
	 * </p>
	 * 
	 * @param yearNum 年度的整数，例如：2016
	 * @author yuanzhonglin
	 * @since Jul 22, 2016
	 */
	public static boolean isLeapYear(int yearNum) {
		// before java 8
		// boolean isLeap = (yearNum % 4 == 0 && yearNum % 100 != 0) || (yearNum % 400 == 0);
		// return isLeap;

		return IsoChronology.INSTANCE.isLeapYear(yearNum);
	}

	/**
	 * 取某个日期所属月份的最后一天(整数形式)
	 * 
	 * @author yuanzhonglin
	 * @since Aug 21, 2016
	 */
	public static int getLastDayIntegerOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 取某个日期所属月份的最后一天（字符串形式）
	 * 
	 * @author yuanzhonglin
	 * @since Aug 21, 2016
	 */
	public static String getLastDayOfMonth(Date date) {
		int lastDay = getLastDayIntegerOfMonth(date);

		return String.valueOf(lastDay);
	}

	/**
	 * 获取某个日期是星期几（中文）
	 * 
	 * @author yuanzhonglin
	 * @since Aug 21, 2016
	 */
	public static String getChineseWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int num = cal.get(Calendar.DAY_OF_WEEK);
		if (num == 1) {
			return "星期日";
		} else if (num == 2) {
			return "星期一";
		} else if (num == 3) {
			return "星期二";
		} else if (num == 4) {
			return "星期三";
		} else if (num == 5) {
			return "星期四";
		} else if (num == 6) {
			return "星期五";
		} else if (num == 7) {
			return "星期六";
		} else {
			return "";
		}
	}

	/**
	 * 将UTC格式的时间字符串转换成日期
	 * 
	 * <pre>
	 * 将2016-10-20T16:00:00.041117Z这样的时间字符串转换成日期。
	 * 
	 * UTC :协调世界时，又称世界统一时间，世界标准时间，国际协调时间。
	 * 中国时区为东八区，所以中国时间与UTC的时差均为+8，也就是UTC+8。
	 * 
	 * 2016-10-20T16:00:00.000000000Z对应的中国时间为2016-10-21 00:00:00。
	 * </pre>
	 * 
	 * @author yuanzhonglin
	 * @since Oct 21, 2016
	 * @since 2016-11-24 modify by sxp 正确的单位转换为：1毫秒=1000*1000纳秒
	 */
	public static Date getDateFromUTCString(String dateStringOfUTC) {
		String prefix = "调用InfluxDBUtils.getDateFromUTCString";
		Date date = null;
		boolean hasNano;

		String dStr = dateStringOfUTC;

		if (dStr == null || dStr.length() == 0) {
			throw new RuntimeException(prefix + "传入的时间字符串不能为空！");
		}

		// 将纳秒值转换为毫秒值
		int pos1 = dStr.lastIndexOf(".");
		int pos2 = dStr.lastIndexOf("Z");
		if (pos2 < 0) {
			throw new RuntimeException(prefix + "传入的时间字符串无效，不含有字符Z！");
		}

		int nano;// 纳秒
		String nanoS;
		if (pos1 >= 0) {
			hasNano = true;
			nanoS = dStr.substring(pos1 + 1, pos2);
			// 1985-04-12T23:20:50.52Z 表示1985年4月12日23时20分50.52秒
			nanoS = nanoS + "000000000";
			nanoS = nanoS.substring(0, 9);// 保证长度为9位

			nano = Integer.parseInt(nanoS);

			int milli = nano / (1000 * 1000);// 毫秒
			if (milli > 999) {
				milli = 999;
			}

			dStr = dStr.substring(0, pos1 + 1) + String.valueOf(milli) + "Z";
		} else {
			hasNano = false;
		}

		// 增加UTC后缀，否则无法转换
		dStr = dStr.replace("Z", " UTC");// 空格+UTC

		SimpleDateFormat df;

		if (hasNano) {
			df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
		} else {
			df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss Z");
		}
		df.setLenient(false);// 将2017-13-01看做是一个非法的日期字符串

		try {
			date = df.parse(dStr);
		} catch (ParseException e) {
			throw new RuntimeException(prefix + "传入的时间字符串不是有效的时间！");
		}

		return date;
	}

	/**
	 * 根据本地时间获取UTC时间的字符串
	 * 
	 * @author yuanzhonglin
	 * @since Oct 21, 2016
	 * @since 2016-11-24 modify by sxp 正确的单位转换为：1毫秒=1000*1000纳秒
	 */
	public static String getUTCStringFromLocalDate(Date localDate) {
		// 本地时间
		Calendar cal = Calendar.getInstance();
		cal.setTime(localDate);

		// 时间偏移量
		int zoneOffset = cal.get(Calendar.ZONE_OFFSET);

		// 夏令时差
		int dstOffset = cal.get(Calendar.DST_OFFSET);

		// 从本地时间里扣除这些差量，即可以取得UTC时间
		cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));

		// 毫秒转换成纳秒
		int millisecond = cal.get(Calendar.MILLISECOND);
		int nano = millisecond * 1000 * 1000;
		if (nano > 999999999) {
			nano = 999999999;
		}
		String nanoS = String.valueOf(nano);
		nanoS = nanoS + "000000000";
		nanoS = nanoS.substring(0, 9);// 保证长度为9位

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		df.setLenient(false);// 将2017-13-01看做是一个非法的日期字符串

		String dateString = df.format(cal.getTime()) + "." + nanoS + "Z";

		return dateString;
	}

	/**
	 * 获取两个时间之间的相差的时间
	 * <p>
	 * 选择合适的时间单位，舍入计算后所得数值的所有位数为4位，例如：1.000 ns , 1.235 ms
	 * <p/>
	 *
	 * @author yuanzhonglin
	 * @since 2017/11/14
	 */
	public static String getElapsedTimeWith4Digits(Date begin, Date end) {
		Objects.requireNonNull(begin, "begin");
		Objects.requireNonNull(end, "end");

		long millis = end.getTime() - begin.getTime();
		TimeUnit unit = chooseUnit(millis);

		double value = (double) millis / MILLISECONDS.convert(1, unit);
		return String.format("%.4g", value) + chineseUinit(unit);
	}

	private static TimeUnit chooseUnit(long millis) {
		if (DAYS.convert(millis, MILLISECONDS) > 0) {
			return DAYS;
		}
		if (HOURS.convert(millis, MILLISECONDS) > 0) {
			return HOURS;
		}
		if (MINUTES.convert(millis, MILLISECONDS) > 0) {
			return MINUTES;
		}
		if (SECONDS.convert(millis, MILLISECONDS) > 0) {
			return SECONDS;
		}
		return MILLISECONDS;
	}

	private static String chineseUinit(TimeUnit unit) {
		switch (unit) {
			case NANOSECONDS:
				return "纳秒";
			case MICROSECONDS:
				return "微秒";
			case MILLISECONDS:
				return "毫秒";
			case SECONDS:
				return "秒";
			case MINUTES:
				return "分钟";
			case HOURS:
				return "小时";
			case DAYS:
				return "天";
			default:
				return "未知的时间单位";
		}
	}


	/**
	 * 获取日期对应的时间戳
	 *
	 * @author yuanzhonglin
	 * @since 2019/3/20
	 */
	public static String getTimestamp(String timeS){
		Date date = DateUtils.stringToDate(timeS, STANDARD_DATETIME_FORMAT);
		return Long.toString(date.getTime());
	}

}
