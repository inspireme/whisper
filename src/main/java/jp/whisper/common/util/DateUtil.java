package jp.whisper.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Date Utility Class
 */
public class DateUtil
{
	public final static int		SECOND					= 1000;
	public final static int		MINUTE					= SECOND * 60;
	public final static int		HOUR					= MINUTE * 60;
	public final static int		DAY						= HOUR * 24;
	public final static int		DAYOFMIDMONTH			= 15;
	public final static String	COMMON_TIME_PATTERN		= "HH:mm";
	public final static String	COMMON_DATE_PATTERN		= "yyyy-MM-dd";
	public final static String	COMMON_DATE1_PATTERN   = "yyyyMMdd";
	public final static String	COMMON_DATETIME_PATTERN	= COMMON_DATE_PATTERN + " " + COMMON_TIME_PATTERN;
	public final static String	COMMON_DATETIME_PATTERN_HHMMSS	= COMMON_DATE_PATTERN + " HH:mm:ss";

	private DateUtil()
	{
		// nothing
	}


	/**
	 * dateToStr
	 *
	 * @param timestamp
	 * @return
	 */
	public static String dateToStr(Date date)
	{
		return DateUtil.dateToStr(date, null);
	}


	/**
	 * dateToStr
	 *
	 * @param timestamp
	 * @return
	 */
	public static String dateToStr(Date date, String aMask)
	{
		String ret = null;
		String mask = aMask;
		if (StringUtil.isBlank(mask))
			mask = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(mask);
		if(date==null){
			ret = "";
		}else{
			ret = sdf.format(date);
		}
		return ret;
	}


	/**
	 * strToDate
	 *
	 * @param timestamp
	 * @return
	 * @throws ParseException
	 */
	public static java.util.Date strToDate(String date) throws ParseException
	{
		return DateUtil.strToDate(date, null);
	}


	/**
	 * strToDate
	 *
	 * @param timestamp
	 * @return
	 * @throws ParseException
	 */
	public static java.util.Date strToDate(String date, String aMask) throws ParseException
	{
		java.util.Date ret = null;
		String mask = aMask;
		if (mask == null || "".equals(mask))
			mask = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(mask);
		ret = sdf.parse(date);
		return ret;
	}


	/**
	 * カレント日付を取得
	 *
	 * @return カレント日付
	 */
	public static Date getNow()
	{
		Calendar c = Calendar.getInstance();
		return c.getTime();
	}


	/**
	 * year年month月day日0時0分0秒の日付オブジェクトを取得
	 *
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date getDate(int year, int month, int day)
	{
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, day);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}


	/**
	 * 今日0時0分0秒の日付オブジェクトを取得
	 *
	 * @return Date
	 */
	public static Date getTodayZeroClock()
	{
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}


	/**
	 * 日付比較
	 *
	 * @param start
	 * @param end
	 * @return boolean
	 */
	public static boolean isBefore(Date start, Date end)
	{
		return start.getTime() - end.getTime() < 0 ? true : false;
	}


	/**
	 * 日付比較（イコールでしたら、TRUEを返す）
	 *
	 * @param start
	 * @param end
	 * @return boolean
	 */
	public static boolean isBeforeOrEqual(Date start, Date end)
	{
		return start.getTime() - end.getTime() <= 0 ? true : false;
	}


	/**
	 * 今日より早いか判断
	 *
	 * @return boolean
	 */
	public static boolean isBeforeToday(Date date)
	{
		Date todayZero = getTodayZeroClock();
		return isBefore(date, todayZero);
	}


	/**
	 * 今日また今日より早いか判断
	 *
	 * @return boolean
	 */
	public static boolean isBeforeOrEqualToday(Date date)
	{
		Date todayZero = getTodayZeroClock();
		return isBeforeOrEqual(date, todayZero);
	}


	/**
	 * dよりcount月前の日付を取得
	 *
	 * @param d
	 * @param count
	 * @return
	 */
	public static Date beforeMonths(Date d, int count)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.MONTH, c.get(Calendar.MONTH) - count);
		return c.getTime();
	}


	/**
	 *  dよりcount日前の日付を取得
	 *
	 * @param d
	 * @param count
	 * @return
	 */
	public static Date beforeDays(Date d, int count)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.MONTH, c.get(Calendar.DAY_OF_MONTH) - count);
		return c.getTime();
	}


	/**
	 * 今日よりcount日前の日付を取得
	 *
	 * @param count
	 * @return
	 */
	public static Date beforeDays(int count)
	{
		return beforeDays(getNow(), count);
	}


	/**
	 * 当時点よりcount時間前の日付を取得
	 *
	 * @param count
	 * @return
	 */
	public static Date beforeHours(int count)
	{
		return beforeHours(getNow(), count);
	}


	/**
	 * dよりcount時間前の日付を取得
	 *
	 * @param d
	 * @param count
	 * @return 目标日期
	 */
	public static Date beforeHours(Date d, int count)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.HOUR, c.get(Calendar.HOUR) - count);
		return c.getTime();
	}

	/**
	 *
	* @Title: beforeYears
	* @Description: dよりcount年前の日付を取得
	* @param d
	* @param count
	* @return
	* @Return: Date
	* @Throws:
	* @Author: bieby
	* @Date: 2013-12-6
	 */
	public static Date beforeYears(Date d, int count)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.YEAR, c.get(Calendar.YEAR) - count);
		return c.getTime();
	}

	/**
	 *  dよりcount分前の日付を取得
	 *
	 * @param d
	 * @param count
	 * @return
	 */
	public static Date beforeMinutes(Date d, int count)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) - count);
		return c.getTime();
	}


	/**
	 * dよりcount分後の日付を取得
	 *
	 * @param d
	 * @param count
	 * @return 目标日期
	 */
	public static Date afterMinutes(Date d, int count)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + count);
		return c.getTime();
	}


	/**
	 * d1とd2の差を計算（ミリ秒単位で返す）
	 *
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static long countMilliSecondsBetween(Date d1, Date d2)
	{
		if (d1 == null || d2 == null)
		{
			throw new IllegalArgumentException("パラメータ「d1」と「d2」は必須である。");
		}
		return Math.abs(d1.getTime() - d2.getTime());
	}


	/**
	 * d1とd2の差を計算（秒単位で返す）
	 *
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static long countSecondsBetween(Date d1, Date d2)
	{
		return countMilliSecondsBetween(d1, d2) / SECOND;
	}


	/**
	 * d1とd2の差を計算（分単位で返す）
	 *
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static long countMinutesBetween(Date d1, Date d2)
	{
		return countMilliSecondsBetween(d1, d2) / MINUTE;
	}


	/**
	 * d1とd2の差を計算（時単位で返す）
	 *
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static long countHoursBetween(Date d1, Date d2)
	{
		return countMilliSecondsBetween(d1, d2) / HOUR;
	}


	/**
	 * d1とd2の差を計算（日単位で返す）
	 *
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static long countDaysBetween(Date d1, Date d2)
	{
		return countMilliSecondsBetween(d1, d2) / DAY;
	}


	public static Date getThisYearBeginning()
	{
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 0);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}


	/**
	 * 今日より早いか判断
	 *
	 * @param date
	 * @return
	 */
	public static boolean isBeforeThisYear(Date date)
	{
		Date yearBeginning = getThisYearBeginning();
		return isBefore(date, yearBeginning);
	}


	/**
	 * 今年の年数を取得
	 *
	 * @return
	 */
	public static int getCurrentYear()
	{
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);

		return currentYear;
	}


	/**
	 * date1とdate2との間の日数を取得
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int diffDate(Date date1, Date date2)
	{
		if (date1 == null || date2 == null)
		{
			return 0;
		}
		Calendar calendar = Calendar.getInstance();
		int zone_offset = calendar.get(Calendar.ZONE_OFFSET);
		int dst_offset = calendar.get(Calendar.DST_OFFSET);
		long d1 = date1.getTime() + zone_offset + dst_offset;
		long d2 = date2.getTime() + zone_offset + dst_offset;
		int intDaysFirst = (int) (d1 / (60 * 60 * 1000 * 24)); // 60*60*1000
		int intDaysSecond = (int) (d2 / (60 * 60 * 1000 * 24));
		return intDaysFirst > intDaysSecond ? intDaysFirst - intDaysSecond : intDaysSecond - intDaysFirst;

	}


	/**
	 * Date to String(フォーマット：yyyy-MM-dd)
	 *
	 * @param aDate
	 * @return
	 */
	public static String formatDate(Date aDate)
	{
		return format(aDate, DateUtil.COMMON_DATE_PATTERN);
	}

	/**
	 *  Date to String(フォーマット：yyyyMMdd)
	 *
	 * @param aDate
	 * @return
	 */
	public static String formatDate1(Date aDate)
	{
		return format(aDate, DateUtil.COMMON_DATE1_PATTERN);
	}

	/**
	 *  Date to String(フォーマット：yyyy-MM-dd HH:mm)
	 *
	 * @param aDate
	 * @return
	 */
	public static String formatDateTime(Date aDate)
	{
		return format(aDate, COMMON_DATETIME_PATTERN);
	}


	/**
	 *  Date to String(フォーマット：yyyy-MM-dd HH:mm:ss)
	 *
	 * @param aDate
	 * @return
	 */
	public static String formatDateTimeByHhmmss(Date aDate)
	{
		return format(aDate, COMMON_DATETIME_PATTERN_HHMMSS);
	}

	/**
	 * 日付をストリングに変換
	 *
	 * @param aDate
	 * @param pattern
	 * @return
	 */
	public static String format(Date aDate, String pattern)
	{
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(aDate);
	}


	/**
	 * ストリングを日付に変換
	 *
	 * @param pattern
	 *            the date pattern the string is in
	 * @param strDate
	 *            a string representation of a date
	 * @return a converted Date object
	 * @see java.text.SimpleDateFormat
	 * @throws ParseException
	 */
	public static final Date parse(String strDate, String pattern) throws ParseException
	{
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(pattern);

		try
		{
			date = df.parse(strDate);
		}
		catch (ParseException pe)
		{
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return (date);
	}


	/**
	 * ストリングを日付に変換（yyyy-MM-dd）
	 *
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String dateStr) throws ParseException
	{
		return parse(dateStr, COMMON_DATE_PATTERN);
	}


	/**
	 * ストリングを日付に変換(yyyy-MM-dd HH:mm)
	 *
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDateTime(String dateStr) throws ParseException
	{
		return parse(dateStr, COMMON_DATETIME_PATTERN);
	}

	/**
	* @Title: getCurrDate
	* @Description: カレント日付を取得 yyyyMMddhhmm
	* @return String
	* @throws ParseException
	* @Return: String
	* @Throws:
	* @Author: yl
	* @Date: 2014-6-25
	*/
	public static String getCurrDate() throws ParseException
	{
		SimpleDateFormat formart = new SimpleDateFormat("yyyyMMddhhmm");
		return formart.format(new Date());
	}

}
