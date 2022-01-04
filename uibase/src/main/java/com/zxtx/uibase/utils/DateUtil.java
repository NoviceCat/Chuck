package com.zxtx.uibase.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * author : novice
 *
 */
public class DateUtil {

    public static final String TAG = DateUtil.class.getSimpleName();

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final int YMDHMS_LINE = 11;
    /**
     * yyyy-MM-dd
     */
    public static final int YMD_LINE = 12;
    /**
     * MM-dd
     */
    public static final int MD_LINE = 13;
    /**
     * HH:mm
     */
    public static final int HM = 14;
    /**
     * HH:mm:ss
     */
    public static final int HMS = 40;

    /**
     * HH:00:00
     */
    public static final int HMS2 = 88;

    /**
     * yyyy/MM/dd HH:00:00
     */
    public static final int HMS_XIE = 51;
    /**
     * yyyy/MM/dd HH:mm:ss
     */
    public static final int HMS_XIE2 = 52;

    /**
     * yyyy/MM/dd
     */
    public static final int HMS_XIE3 = 53;
    /**
     * yyyy年MM月dd日
     */
    public static final int YMD_CHIN = 22;
    /**
     * MM月dd日
     */
    public static final int MD_CHIN = 23;
    /**
     * MM月dd日 HH:mm
     */
    public static final int MDHM_CHIN = 24;
    /**
     * MM-dd HH:mm
     */
    public static final int MDHM_LINE = 25;
    /**
     * yyyy.MM.dd
     */
    public static final int YMD_DOT = 32;
    /**
     * yyyy.MM
     */
    public static final int YMD_DOT_DAY = 35;
    /**
     * yyyy
     */
    public static final int YEAR = 36;

    /**
     * MM.dd
     */
    public static final int MD_DOT = 37;
    /**
     * MM
     */
    public static final int MM = 38;
    /**
     * DD
     */
    public static final int DD = 39;

    /**
     * yyyy-MM-dd HH:mm
     */
    public static final int YMD_HM = 33;

    public static final int YMD_HH_MM_SS = 34;
    public static final int YMD_HH_MM_SS2 = 41;
    public static final int MMINUTE = 42;
    public static final int YM = 43;
    public static final int YM_LINE = 45;


    public static SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat ymdhm = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static SimpleDateFormat ymdhm2 = new SimpleDateFormat("yyyy.MM.dd HH:mm");
    public static SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat year = new SimpleDateFormat("yyyy");
    public static SimpleDateFormat ymd_dot = new SimpleDateFormat("yyyy.MM.dd");
    public static SimpleDateFormat ymd_dot_day = new SimpleDateFormat("yyyy.MM");
    public static SimpleDateFormat ym = new SimpleDateFormat("yyyy年MM月");
    public static SimpleDateFormat ym_line = new SimpleDateFormat("yyyy-MM");
    public static SimpleDateFormat mm = new SimpleDateFormat("MM");
    public static SimpleDateFormat dd = new SimpleDateFormat("dd");
    public static SimpleDateFormat mminute = new SimpleDateFormat("mm:ss");
    public static SimpleDateFormat nyr = new SimpleDateFormat("yyyy年MM月dd日");
    public static SimpleDateFormat yr = new SimpleDateFormat("MM月dd日");
    public static SimpleDateFormat yrhm = new SimpleDateFormat("MM月dd日 HH:mm");
    public static SimpleDateFormat md = new SimpleDateFormat("MM-dd");
    public static SimpleDateFormat mddot = new SimpleDateFormat("MM.dd");
    public static SimpleDateFormat hm = new SimpleDateFormat("HH:mm");
    public static SimpleDateFormat hms = new SimpleDateFormat("HH:mm:ss");
    public static SimpleDateFormat hms2 = new SimpleDateFormat("HH:00:00");
    public static SimpleDateFormat mdhm = new SimpleDateFormat("MM-dd HH:mm");
    public static SimpleDateFormat yrhms = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

    public static SimpleDateFormat yrhms_xie = new SimpleDateFormat("yyyy/MM/dd HH:00:00");
    public static SimpleDateFormat yrhms_xie2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    public static SimpleDateFormat yrhms_xie3 = new SimpleDateFormat("yyyy/MM/dd");

    /**
     * 计算时间差  比如  一天前   一个小时前
     *
     * @param milliseconds
     * @return
     */
    public static String getTimeString(long milliseconds) {
        try {
            StringBuffer result = new StringBuffer();
            long time = System.currentTimeMillis() - (milliseconds * 1000);
            long mill = (long) Math.ceil(time / 1000);//秒前
            long minute = (long) Math.ceil(time / 60 / 1000.0f);// 分钟前
            long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);// 小时
            long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// 天前

            if (day - 1 > 0 && day - 1 < 30) {
                result.append(day + "天");
            } else if (day - 1 >= 30) {
                result.append(Math.round((day - 1) / 30) + "个月");
            } else if (hour - 1 > 0) {
                if (hour >= 24) {
                    result.append("1天");
                } else {
                    result.append(hour + "小时");
                }
            } else if (minute - 1 > 0) {
                if (minute == 60) {
                    result.append("1小时");
                } else {
                    result.append(minute + "分钟");
                }
            } else if (mill - 1 > 0) {
                if (mill == 60) {
                    result.append("1分钟");
                } else {
                    result.append(mill + "秒");
                }
            } else {
                result.append("刚刚");
            }
            if (!result.toString().equals("刚刚")) {
                result.append("前");
            }
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取传入时间的对应时间long值
     *
     * @param calendar
     * @return
     */
    public static long getTimeLong(Calendar calendar) {
        return calendar.getTimeInMillis();
    }

    /**
     * 根据类型获取传入时间calendar的对应字符串
     *
     * @param calendar
     * @param type
     * @return
     */
    public static String getTimeString(Calendar calendar, int type) {
        return getTimeString(calendar.getTime(), type);
    }

    /**
     * 将long类型时间转为对应type的字符串
     *
     * @param millis
     * @return
     */
    public static String getTimeString(long millis, int type) {
        if (millis == 0)
            return "";
        return getTimeString(new Date(millis), type);
    }

    /**
     * 获取前一天日期
     *
     * @param date
     * @return
     * @author wangxj
     * @version 4.3  2016/6/22
     */
    public static String getBeforeDay(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String result = null;
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(date));
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            Date beforeDay = calendar.getTime();
            result = sdf.format(beforeDay);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获得指定日期的前一天
     *
     * @param specifiedDay 指定日期  yyyy-MM-dd
     * @param differ       往前相差几天
     * @return 返回相差几天的前面日期  yyyy-MM-dd
     */
    public static String getSpecifieDifferDayBefore(String specifiedDay, int differ) {

        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = ymd.parse(specifiedDay);
        } catch (ParseException e) {
            //e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - differ);
        return getTimeString(c.getTime(), YMD_LINE);
    }


    /**
     * 获得指定日期的后面几天
     *
     * @param specifiedDay 指定日期 yyyy-MM-dd
     * @param differ       相差几天
     *                     需要返回的日期格式，例如：yyyy-MM-dd
     * @return 返回相差几天的日期yyyy-MM-dd
     */
    public static String getSpecifieDifferDayNext(String specifiedDay, int differ) {

        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = ymd.parse(specifiedDay);
        } catch (ParseException e) {
            //e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + differ);
//        String dayBefore = new SimpleDateFormat(pattern).format(c.getTime());
//        getTimeString(c.getTime(), YMD_LINE);

        return getTimeString(c.getTime(), YMD_LINE);
    }


    /**
     * /**
     * 将Date类型时间转为对应type的字符串
     *
     * @param type 类似这种  yyyy-MM-dd
     * @return
     */
    @SuppressWarnings("finally")
    public static String getTimeString(Date date, int type) {
        String retStr = "";
        try {
            switch (type) {
                case YMDHMS_LINE:
                    retStr = ymdhms.format(date);
                    break;

                case YMD_LINE:
                    retStr = ymd.format(date);
                    break;
                case MD_LINE:
                    retStr = md.format(date);
                    break;
                case HM:
                    retStr = hm.format(date);
                    break;
                case YMD_CHIN:
                    retStr = nyr.format(date);
                    break;
                case HMS_XIE:
//                  yyyy/MM/dd HH:00:00
                    retStr = yrhms_xie.format(date);
                    break;
                case HMS_XIE2:
//                  yyyy/MM/dd HH:mm:ss
                    retStr = yrhms_xie2.format(date);
                    break;
                case HMS_XIE3:
//                  yyyy/MM/dd
                    retStr = yrhms_xie3.format(date);
                    break;
                case MD_CHIN:
                    retStr = yr.format(date);
                    break;
                case MDHM_CHIN:
                    retStr = yrhm.format(date);
                    break;
                case YMD_DOT:
                    retStr = ymd_dot.format(date);
                    break;
                case YMD_DOT_DAY:
                    retStr = ymd_dot_day.format(date);
                    break;
                case YM:
                    retStr = ym.format(date);
                    break;
                case YM_LINE:
                    retStr = ym_line.format(date);
                    break;
                case MDHM_LINE:
                    retStr = mdhm.format(date);
                    break;
                case YMD_HH_MM_SS:
                    retStr = yrhms.format(date);
                    break;
                case YMD_HM:
                    retStr = ymdhm.format(date);
                    break;
                case YEAR:
                    retStr = year.format(date);
                    break;
                case MD_DOT:
                    retStr = mddot.format(date);
                    break;
                case MM:
                    retStr = mm.format(date);
                    break;
                case HMS:
                    retStr = hms.format(date);
                    break;
                case HMS2:
                    retStr = hms2.format(date);
                    break;
                case YMD_HH_MM_SS2:
                    retStr = ymdhm2.format(date);
                    break;
                case MMINUTE:
                    retStr = mminute.format(date);
                    break;
                case DD:
                    retStr = dd.format(date);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            LogUtils.d(TAG, "TimeTransformation", "时间转换字符串失败！");
        } finally {
            return retStr;
        }
    }

    /**
     * 根据类型获取传入字符串的时间 、-1为当前时间
     *
     * @param date
     * @param type
     * @return
     */
    @SuppressWarnings("finally")
    public static Calendar getTimeCalendar(String date, int type) {
        Calendar calendar = Calendar.getInstance();
        try {
            switch (type) {
                case YMDHMS_LINE:
                    calendar.setTime(ymdhms.parse(date));
                    break;
                case YMD_LINE:
                    calendar.setTime(ymd.parse(date));
                    break;
                case YMD_CHIN:
                    calendar.setTime(nyr.parse(date));
                    break;
                case YMD_DOT:
                    calendar.setTime(ymd_dot.parse(date));
                    break;
                case MD_CHIN:
                    calendar.setTime(yr.parse(date));
                    break;
                case MD_LINE:
                    calendar.setTime(md.parse(date));
                    break;
                case HM:
                    calendar.setTime(hm.parse(date));
                    break;
                case MDHM_CHIN:
                    calendar.setTime(yrhm.parse(date));
                    break;
                case MDHM_LINE:
                    calendar.setTime(mdhm.parse(date));
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            LogUtils.d(TAG, "TimeTransformation", "字符串转换时间失败！");
        } finally {
            return calendar;
        }
    }

    /**
     * 将一格式字符串转为另一格式字符串
     *
     * @param date    转换前时间字符串
     * @param newtype 转换后格式
     * @param oldtype 转换前格式
     * @return
     */
    public static String transformTimeString(String date, int newtype, int oldtype) {
        try {
            if (newtype == oldtype) {
                return date;
            }
            String newdate = getTimeString(getTimeCalendar(date, oldtype), newtype);
            return TextUtils.isEmpty(newdate) ? date : newdate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 根据类型比较agodate与afterdate时间前后：
     *
     * @param agodate
     * @param afterdate
     * @param type
     * @return 0为相等、1为agodate前、-1为afterdate前
     */
    public static int compareTime(String agodate, String afterdate, int type) {
        try {
            if (agodate.equals(afterdate)) {
                return 0;
            } else if (getTimeLong(getTimeCalendar(agodate, type)) > getTimeLong(getTimeCalendar(
                    afterdate, type))) {
                return 1;
            }
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 根据类型比较agodate与afterdate时间前后：
     *
     * @param agodate   今天日期
     * @param afterdate 选中的日期
     * @param type
     * @return 返回相差几天的时间
     */
    public static int compareDayTime(String agodate, String afterdate, int type) {
        try {
            if (agodate.equals(afterdate)) {
                return 0;
            } else {
                long time = getTimeLong(getTimeCalendar(agodate, type)) - getTimeLong(getTimeCalendar(
                        afterdate, type));
                if (time > 0) {
                    int floor = (int) Math.floor(time / (24 * 60 * 60 * 1000));
                    return floor;
                } else {

                    return 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据类型比较agodate与afterdate时间前后：
     *
     * @param beginWeekDay   本周开始时间
     * @param endWeekDay     本周结束时间
     * @param compareWeekDay 要比较的日期
     * @param type
     * @return 返回是否在同一周
     */
    public static boolean isCurrentWeek(String beginWeekDay, String endWeekDay, String compareWeekDay, int type) {
        try {
            long timeBeginLong = getTimeLong(getTimeCalendar(beginWeekDay, type));
            long timEndLong = getTimeLong(getTimeCalendar(endWeekDay, type));
            long timeCompareBeginLong = getTimeLong(getTimeCalendar(compareWeekDay, type));

            if (timeBeginLong < timeCompareBeginLong && timeCompareBeginLong < timEndLong) {
                return true;
            }
            LogUtils.e("beginWeekDay = " + beginWeekDay + "--endWeekDay =" + endWeekDay + "--compareWeekDay=" + compareWeekDay
                    + "--timeBeginLong = " + timeBeginLong + "--timEndLong = " + timEndLong + "--timeCompareBeginLong =" + timeCompareBeginLong
            );
            return false;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据类型比较agodate与afterdate时间前后：
     *
     * @param agodate   时间1
     * @param agotype   时间1类型
     * @param afterdate 时间2
     * @param aftertype 时间2类型
     * @return 0为相等、1为agodate前、-1为afterdate前
     */
    public static int compareTime(String agodate, int agotype,
                                  String afterdate, int aftertype) {
        if (agotype != aftertype) {
            afterdate = transformTimeString(afterdate, agotype, aftertype);
        }
        return compareTime(agodate, afterdate, agotype);
    }

    /**
     * 西方默认周日为一周的第一天，此方法获取以周一为第一天的一周的第N天
     *
     * @return 第几天. 例：周一返回 1；周日返回 7
     */
    public static int getOurWeek(Calendar calendar) {
        int week = calendar.get(Calendar.DAY_OF_WEEK);// 西方算法、周日为一周的第一天
        return week == 1 ? 7 : week - 1;// 因周日为一周的第一天，故减1
    }

    /**
     * 根据1`7获取对应的星期 返回周一周二
     *
     * @param context
     * @param postion
     * @return
     */
    public static String getWeek(Context context, int postion) {
        switch (postion) {
            case 0:
                return "周一";
            case 1:
                return "周二";
            case 2:
                return "周三";
            case 3:
                return "周四";
            case 4:
                return "周五";
            case 5:
                return "周六";
            default:
                return "周日";
        }
    }

    public static boolean isWeekend(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        switch (week) {
            case 0:
            case 6:
                return true;
        }
        return false;
    }

    /**
     * 两个时间是否是同一周
     */
    public static boolean isSameWeek(long date1, long date2) {
        return isSameWeek(getTimeString(date1, YMD_LINE), getTimeString(date2, YMD_LINE), YMD_LINE);
    }

    /**
     * 两个时间是否是同一周
     */
    public static boolean isSameWeek(String date1, String date2) {
        return isSameWeek(date1, date2, YMD_LINE);
    }

    /**
     * 两个时间是否是同一周
     *
     * @param type 转化时间类型
     */
    public static boolean isSameWeek(String date1, String date2, int type) {
        try {
            Calendar cal1 = getTimeCalendar(date1, type);
            Calendar cal2 = getTimeCalendar(date2, type);
            cal1.add(Calendar.DAY_OF_YEAR, -1);
            cal2.add(Calendar.DAY_OF_YEAR, -1);
            int cal2Month = cal2.get(Calendar.MONTH);
            int cal1Month = cal1.get(Calendar.MONTH);
            int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
            // subYear==0,说明是同一年
            if (subYear == 0) {
                if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
                        .get(Calendar.WEEK_OF_YEAR))
                    return true;
            }
            // 例子:cal1是"2005-1-1"，cal2是"2004-12-25"
            // java对"2004-12-25"处理成第52周
            // "2004-12-26"它处理成了第1周，和"2005-1-1"相同了
            // 大家可以查一下自己的日历
            // 处理的比较好
            // 说明:java的一月用"0"标识，那么12月用"11"

            else if (subYear == 1 && cal2Month == 11) {
                if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
                        .get(Calendar.WEEK_OF_YEAR))
                    return true;
            }
            // 例子:cal1是"2004-12-31"，cal2是"2005-1-1"
            else if (subYear == -1 && cal1Month == 11) {
                if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
                        .get(Calendar.WEEK_OF_YEAR))
                    return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断是否是同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(String date1, String date2) {
        return isSameDay(date1, date2, YMDHMS_LINE);
    }

    /**
     * 判断是否是同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(String date1, String date2, int type) {
        try {
            Calendar cal1 = getTimeCalendar(date1, type);
            Calendar cal2 = getTimeCalendar(date2, type);
            int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
            if (subYear != 0) return false;
            int subMonth = cal1.get(Calendar.MONTH) - cal2.get(Calendar.MONTH);
            if (subMonth != 0) return false;
            int subDay = cal1.get(Calendar.DAY_OF_MONTH) - cal2.get(Calendar.DAY_OF_MONTH);
            return subDay == 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 计算宝宝几岁
     *
     * @param nCalendar
     * @param oCalendar
     * @return
     */
    public static int CalculatorYear(Calendar nCalendar, Calendar oCalendar) {
        int year1 = oCalendar.get(Calendar.YEAR);
        int year2 = nCalendar.get(Calendar.YEAR);
        int month1 = oCalendar.get(Calendar.MONTH);
        int month2 = nCalendar.get(Calendar.MONTH);
        int year = year2 - year1;
        if (compareTo(nCalendar, oCalendar)) // 计算天时向月借了一个月
            month2 -= 1;
        if (month1 > month2)
            year -= 1;
        return year;
    }

    /**
     * 计算宝宝几个月
     *
     * @param nCalendar
     * @param oCalendar
     * @return
     */
    public static int CalculatorMonth(Calendar nCalendar, Calendar oCalendar) {
        int month1 = oCalendar.get(Calendar.MONTH);
        int month2 = nCalendar.get(Calendar.MONTH);
        int month = 0;
        if (compareTo(nCalendar, oCalendar)) // 计算天时向月借了一个月
            month2 -= 1;
        if (month2 >= month1)
            month = month2 - month1;
        else if (month2 < month1) // 借一整年
            month = 12 + month2 - month1;
        return month;
    }

    /**
     * 计算宝宝几天
     *
     * @param nCalendar
     * @param oCalendar
     * @return
     */
    public static int CalculatorDay(Calendar nCalendar, Calendar oCalendar) {
        int day11 = oCalendar.get(Calendar.DAY_OF_MONTH);
        int day21 = nCalendar.get(Calendar.DAY_OF_MONTH);

        if (day21 >= day11) {
            return day21 - day11;
        } else {// 借一整月
            Calendar cal = Calendar.getInstance();
            cal.setTime(nCalendar.getTime());
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.add(nCalendar.DAY_OF_MONTH, -1);
            return cal.getActualMaximum(Calendar.DATE) + day21 - day11;
        }
    }

    /**
     * 当前日比较
     *
     * @return
     */
    public static boolean compareTo(Calendar nCalendar, Calendar oCalendar) {
        return oCalendar.get(Calendar.DAY_OF_MONTH) > nCalendar
                .get(Calendar.DAY_OF_MONTH);
    }


    /**
     * 时间格式显示规则2
     * 1.当天的时间段内，显示格式为“今天  小时：分钟”。比如：今天  10：10。
     * 2.其他时间，不管何时，显示格式为“月-日  小时：分钟”。比如：12-12  12：12。
     */
    public static String generateTime(long Time) {
        String returnTime = "";
        try {
            Calendar calendar = Calendar.getInstance();
            int curYear = calendar.get(Calendar.YEAR);
            int curMonth = calendar.get(Calendar.MONTH);
            int curDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            calendar.setTimeInMillis(Time);
            int msgYear = calendar.get(Calendar.YEAR);
            int msgMonth = calendar.get(Calendar.MONTH);
            int msgDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            if (curYear == msgYear && curMonth == msgMonth
                    && curDayOfMonth == msgDayOfMonth) {
                returnTime = "今天" + " " + String.format("%02d:%02d",
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE));
            } else {
                returnTime = String.format("%02d-%02d %02d:%02d",
                        msgMonth + 1, msgDayOfMonth,
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnTime;
    }

    /**
     * 时间格式显示规则-简洁型
     * 当天内，显示格式为“16：04”（小时-分钟）。
     * 昨天的消息，显示格式为“昨天”。
     * 昨天以前的，显示格式为“09-19”（月份-日期）。
     */
    public static String switchToSimpleTime(String switchTime) {
        try {
            String returnTime;
            Calendar calendarTime = getTimeCalendar(switchTime, DateUtil.YMDHMS_LINE);
            Long time = getTimeLong(calendarTime);
            Calendar calendar = Calendar.getInstance();
            int curYear = calendar.get(Calendar.YEAR);
            int curMonth = calendar.get(Calendar.MONTH);
            int curDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            calendar.setTimeInMillis(time);
            int msgYear = calendar.get(Calendar.YEAR);
            int msgMonth = calendar.get(Calendar.MONTH);
            int msgDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            if (curYear == msgYear && curMonth == msgMonth
                    && curDayOfMonth == msgDayOfMonth) {
                returnTime = String.format("%02d:%02d",
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE));
            } else if (curYear == msgYear && curMonth == msgMonth
                    && curDayOfMonth - 1 == msgDayOfMonth) {
                returnTime = "昨天";
            } else {
                returnTime = String
                        .format("%02d-%02d", msgMonth + 1, msgDayOfMonth);
            }
            return returnTime;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * @param switchTime long时间戳 string格式
     *                   时间格式显示规则-简洁型
     *                   当天内，显示格式为“16：04”（小时-分钟）。
     *                   昨天的消息，显示格式为“昨天”。
     *                   昨天以前的，显示格式为“09-19”（月份-日期）。
     */
    public static String switchToSimpleTimeFromLongStr(String switchTime) {
        try {
            String returnTime;
            Long time = Long.parseLong(switchTime);
            Calendar calendar = Calendar.getInstance();
            int curYear = calendar.get(Calendar.YEAR);
            int curMonth = calendar.get(Calendar.MONTH);
            int curDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            calendar.setTimeInMillis(time);
            int msgYear = calendar.get(Calendar.YEAR);
            int msgMonth = calendar.get(Calendar.MONTH);
            int msgDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            Log.e("MessageTabFragmentNEW", "curMonth:  " + curMonth + "   curDayOfMonth:  " + curDayOfMonth + "   msgMonth: " + msgMonth + "  msgDayOfMonth :   " + msgDayOfMonth);
            if (curYear == msgYear && curMonth == msgMonth
                    && curDayOfMonth == msgDayOfMonth) {
                returnTime = String.format("%02d:%02d",
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE));
            } else if (curYear == msgYear && curMonth == msgMonth
                    && curDayOfMonth - 1 == msgDayOfMonth) {
                returnTime = "昨天";
            } else {
                returnTime = String
                        .format("%02d-%02d", msgMonth + 1, msgDayOfMonth);
            }
            return returnTime;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 如果时间是今天内的，输出格式：今天 HH:mm
     * 其他日期，输出格式：MM-dd HH:mm
     * 其他年份，输出格式：yyyy-MM-dd HH:mm
     *
     * @param time
     * @param todayTextFormat
     * @return
     */
    public static String formatWithToday(long time, String todayTextFormat) {
        try {
            Calendar target = Calendar.getInstance();
            target.setTimeInMillis(time);
            Calendar curr = Calendar.getInstance();
            if (curr.get(Calendar.YEAR) == target.get(Calendar.YEAR)) {
                if (curr.get(Calendar.DAY_OF_YEAR) == target.get(Calendar.DAY_OF_YEAR)) {
                    return String.format(todayTextFormat, String.format("%02d:%02d", target.get(Calendar.HOUR_OF_DAY),
                            target.get(Calendar.MINUTE)));
                } else {
                    return formatQuickly("MM-dd HH:mm", time);
                }
            } else {
                return formatQuickly("yyyy-MM-dd HH:mm", time);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 格式化时间
     */
    public static String formatDayWithThisYear(long time, String thisYearFormat, String otherYearFormat) {
        int result = compareYear(time, System.currentTimeMillis());
        return formatQuickly(result == 0 ? thisYearFormat : otherYearFormat, time);
    }

    /**
     * 格式化时间
     */
    public static String formatQuickly(String format, long time) {
        try {
            Date date = new Date(time);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取两个时间相差多少年
     *
     * @param time1
     * @param time2
     * @return
     */
    public static int compareYear(long time1, long time2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time1);
        int year1 = calendar.get(Calendar.YEAR);
        calendar.setTimeInMillis(time2);
        int year2 = calendar.get(Calendar.YEAR);
        return year1 - year2;
    }

    /**
     * 获取两个时间相差多少个月
     *
     * @param time1
     * @param time2
     * @return
     */
    public static int compareMonth(long time1, long time2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time1);
        int month1 = calendar.get(Calendar.MONTH);
        calendar.setTimeInMillis(time2);
        int month2 = calendar.get(Calendar.MONTH);
        return month1 - month2;
    }

    /**
     * 获取两个时间是否是同一天
     *
     * @param time1 2012-2-3
     * @param time2 2014-3-3
     * @return true
     */
    public static boolean isSameDay(long time1, long time2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time1);
        int day1 = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.setTimeInMillis(time2);
        int day2 = calendar.get(Calendar.DAY_OF_MONTH);
        return day1 == day2;
    }

    public static boolean isSameDays(long time1, long time2) {
        Date date = new Date(time1);
        Calendar calDateA = Calendar.getInstance();
        calDateA.setTime(date);
        date.setTime(time2);
        Calendar calDateB = Calendar.getInstance();
        calDateB.setTime(date);

        return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
                && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
                && calDateA.get(Calendar.DAY_OF_MONTH) == calDateB
                .get(Calendar.DAY_OF_MONTH);

    }

    public static int getMonth(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.MONTH);
    }

    public static int getYear(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 判断时间相差了几天
     */
    public static long differDays(String nowTime, String time) {
        long days = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = sdf.parse(nowTime);
            Date d2 = sdf.parse(time);
            long diff = d1.getTime() - d2.getTime();
            days = diff / (1000 * 60 * 60 * 24);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return days;
    }


    /**
     * 获取某年某月的的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static int getMonthLastDay(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 获取时间是上午还是下午
     *
     * @param dateStr yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getAMPM(String dateStr) {
        try {
            GregorianCalendar ca = new GregorianCalendar();
            Date date = ymdhms.parse(dateStr);
            ca.setTime(date);
            StringBuffer sb = new StringBuffer(ymd.format(date));
            int apm = ca.get(GregorianCalendar.AM_PM);
            if (apm == 0) {
                return sb.append(" 上午").toString();
            } else {
                return sb.append(" 下午").toString();
            }
        } catch (Exception e) {
            return dateStr;
        }
    }

    /**
     * 获取时间是上午还是下午
     *
     * @param dateStr yyyy-MM-dd HH:mm:ss
     * @return X月X日周X X午
     */
    public static String getDateAMPM(String dateStr) {
        String[] weeks = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        try {
            Calendar ca = Calendar.getInstance();
            Date date = ymdhms.parse(dateStr);
            ca.setTime(date);
            int month = ca.get(Calendar.MONTH) + 1;
            int day = ca.get(Calendar.DAY_OF_MONTH);
            int dayOfWeek = ca.get(Calendar.DAY_OF_WEEK) - 1;
            int hour = ca.get(Calendar.HOUR_OF_DAY);
            String noonType;
            if (hour < 12) {
                noonType = "上午";
            } else {
                noonType = "下午";
            }

            return month + "月" + day + "日" + weeks[dayOfWeek] + " " + noonType;
        } catch (Exception e) {
            return dateStr;
        }
    }


    /**
     * 判断是否同个月
     *
     * @param time1 时间戳
     * @param time2 时间戳
     * @return
     */
    public static boolean isSameMonths(long time1, long time2) {
        Date date = new Date(time1);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        date.setTime(time2);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date);
        int year1 = calendar1.get(Calendar.YEAR);
        int year2 = calendar2.get(Calendar.YEAR);
        int month1 = calendar1.get(Calendar.MONTH);
        int month2 = calendar2.get(Calendar.MONTH);
        System.out.println(year1 + "  " + month1);
        System.out.println(year2 + "  " + month2);
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH);
    }

    /**
     * 判断是否同个月
     *
     * @param time1 yyyy-MM-dd HH:mm
     * @param time2 yyyy-MM-dd HH:mm
     * @return
     */
    public static boolean isSameMoneh(String time1, String time2) {
        try {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(ymdhm.parse(time1));
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(ymdhm.parse(time2));
            return calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断指定年月是否是当前年月
     *
     * @param year
     * @param month
     * @return
     */
    public static boolean isCurrentMonth(int year, int month) {

        Calendar now = Calendar.getInstance();
        if (now.get(Calendar.YEAR) == year && (now.get(Calendar.MONTH) + 1) == month) {
            return true;
        }
        return false;
    }

    /**
     * 比较是否大于当前的月份
     *
     * @param year
     * @param month
     * @return
     */
    public static boolean compareMonth(int year, int month) {
        Calendar now = Calendar.getInstance();
        if (year > now.get(Calendar.YEAR)) {
            return true;
        } else if (now.get(Calendar.YEAR) == year) {
            if (now.get(Calendar.MONTH) + 1 < month) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获取当前日
     *
     * @return
     */
    public static int getNowDay() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前月
     *
     * @return
     */
    public static int getCurrentMonth() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.MONTH);
    }

    /**
     * 获取当前年
     *
     * @return
     */
    public static int getCurrentYear() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.YEAR);
    }

    /**
     * 把时间转成分钟时长
     *
     * @param timeLength second
     * @return
     */
    public static String toTimeBySecond(int timeLength) {
        int minute = timeLength / 60;
        if (minute >= 60) {
            minute = minute % 60;
        }
        int second = timeLength % 60;
        StringBuffer sb = new StringBuffer();
        if (minute >= 10) {
            sb.append(minute);
        } else {
            sb.append("0" + minute);
        }
        sb.append(":");
        if (second >= 10) {
            sb.append(second);
        } else {
            sb.append("0" + second);
        }
        return sb.toString();
    }

    /**
     * 两个时间是否相差在 timeLimit 内
     *
     * @param time1
     * @param time2
     * @return
     */
    public static boolean isCloseEnough(long time1, long time2, long timeLimit) {
        long delta = time1 - time2;
        if (delta < 0) {
            delta = -delta;
        }
        return delta < timeLimit;
    }


    /**
     * 查询年龄
     *
     * @param babyYear
     * @param babyMonth
     * @param babyDay
     * @return
     */
    public static int[] getAgeStr(int babyYear, int babyMonth, int babyDay,
                                  int curYear, int curMonth, int curDay) {
        int datas[] = new int[3];
        int diffMonth = 0;
        int diffDay = 0;
        int diffYear = 0;
        if (curYear > babyYear) {//非同年
            diffYear = curYear - babyYear;
            if (curMonth < babyMonth) {
                diffYear--;
            }
            if (curDay >= babyDay) {//不跨月
                // 当前月份+前一年剩余月份+中间间隔年的月份
                diffMonth = (curMonth + (12 - babyMonth)) % 12;
                if (getDaysByMonth(babyYear, babyMonth) == babyDay && getDaysByMonth(curYear, curMonth) == curDay) {
                    diffDay = 0;
                } else if (getDaysByMonth(babyYear, babyMonth) == babyDay) {//如果宝宝是当月最后一天出生
                    diffDay = curDay;
                } else if (getDaysByMonth(curYear, curMonth) == curDay) {//如果当天是当月最后一天
                    diffDay = getDaysByMonth(babyYear, babyMonth) - babyDay;
                } else {
                    diffDay = curDay - babyDay;
                }
            } else {
                //当前月份+前一年剩余月份+中间间隔年的月份-1  5 - 4
                diffMonth = (curMonth + (12 - babyMonth)) % 12;
                if (getDaysByMonth(babyYear, babyMonth) == babyDay && getDaysByMonth(curYear, curMonth) == curDay) {//如果宝宝是当月最后一天出生
                    diffDay = 0;
                } else if (getDaysByMonth(babyYear, babyMonth) == babyDay) {//如果宝宝是当月最后一天出生
                    diffDay = curDay;
                    diffMonth--;
                } else if (getDaysByMonth(curYear, curMonth) == curDay) {//如果当天是当月最后一天
                    diffDay = getDaysByMonth(babyYear, babyMonth) - babyDay;
                } else {
                    diffMonth--;
                    //当前天数+ 那个月剩余天数
                    if (curMonth > 1) {
                        if (getDaysByMonth(curYear, curMonth - 1) > babyDay) {
                            diffDay = curDay + (getDaysByMonth(babyYear, babyMonth - 1) - babyDay);
                        } else {
                            diffDay = curDay + (getDaysByMonth(babyYear, babyMonth) - babyDay);
                        }
                    } else {
                        if (getDaysByMonth(curYear - 1, 12) > babyDay) {//前一个月
                            diffDay = curDay + (getDaysByMonth(babyYear - 1, 12) - babyDay);
                        } else {
                            diffDay = curDay + (getDaysByMonth(babyYear, babyMonth) - babyDay);
                        }
                    }
                }
            }
        } else {//同年
            if (curDay >= babyDay) {//不跨月
                diffMonth = curMonth - babyMonth;
                if (getDaysByMonth(babyYear, babyMonth) == babyDay && getDaysByMonth(curYear, curMonth) == curDay) {
                    diffDay = 0;
                } else if (getDaysByMonth(babyYear, babyMonth) == babyDay) {//如果宝宝是当月最后一天出生
                    diffMonth--;
                    diffDay = curDay;
                } else if (getDaysByMonth(curYear, curMonth) == curDay) {//如果当天是是本月最后一天
                    diffDay = getDaysByMonth(babyYear, babyMonth) - babyDay;
                } else {
                    diffDay = curDay - babyDay;
                }
            } else {//跨月
                diffMonth = curMonth - babyMonth;
                if (getDaysByMonth(babyYear, babyMonth) == babyDay && getDaysByMonth(curYear, curMonth) == curDay) {//如果宝宝是当月最后一天出生
                    diffDay = 0;
                } else if (getDaysByMonth(babyYear, babyMonth) == babyDay) {//如果宝宝是当月最后一天出生
                    diffMonth--;
                    diffDay = curDay;
                } else if (getDaysByMonth(curYear, curMonth) == curDay) {//如果当天是当月最后一天
                    diffDay = getDaysByMonth(babyYear, babyMonth) - babyDay;
                } else {
                    diffMonth--;
                    if (curMonth > 1) {
                        if (getDaysByMonth(curYear, curMonth - 1) > babyDay) {
                            diffDay = curDay + (getDaysByMonth(babyYear, babyMonth - 1) - babyDay);
                        } else {//当前天数+ 那个月剩余天数
                            diffDay = curDay + (getDaysByMonth(babyYear, babyMonth) - babyDay);
                        }
                    } else {
                        if (getDaysByMonth(curYear - 1, 12) > babyDay) {//前一个月
                            diffDay = curDay + (getDaysByMonth(babyYear - 1, 12) - babyDay);
                        } else {//当前天数+ 那个月剩余天数
                            diffDay = curDay + (getDaysByMonth(babyYear, babyMonth) - babyDay);
                        }
                    }
                }
            }
        }
        if (diffMonth == 0 && diffYear == 0) {//仅限于小于一个月
            if (diffDay > 0) {
                diffDay = diffDay + 1;
            }
        }
        if (diffMonth < 0) {
            diffMonth = 11;
            diffYear--;
        }

        datas[0] = diffMonth;
        datas[1] = diffDay;
        datas[2] = diffYear;
        return datas;
    }

    /**
     * 查询当月天数
     *
     * @param month
     * @return
     */
    public static int getDaysByMonth(int year, int month) {
        if (month == 2) {
            if (isLeapYear(year)) {
                return 29;
            }
            return 28;
        } else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
                || month == 10 || month == 12) {
            return 31;
        } else {
            return 30;
        }
    }

    /**
     * 查询当年天数
     *
     * @param year
     * @return
     */
    public static int getDaysInYear(int year) {
        if (isLeapYear(year)) {
            return 366;
        } else {
            return 365;
        }
    }


    /**
     * 是否闰年
     *
     * @param year
     * @return
     */
    public static boolean isLeapYear(int year) {
        return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0);
    }

    /*
     * 将时间转换为时间戳
     */
    public static long dateToStamp(String s) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        return date.getTime();
    }

    /**
     * 比较时间
     */
    public static boolean isThisTime(long time, String pattern) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String param = sdf.format(date);//参数时间
        String now = sdf.format(new Date());//当前时间
        if (param.equals(now)) {
            return true;
        }
        return false;
    }

    /**
     * 判断选择的日期是否是今天
     *
     * @param time
     * @return
     */
    public static boolean isToday(long time) {
        return isThisTime(time, "yyyy-MM-dd");
    }

    /**
     * 判断当前时间是否是当天的临界值
     *
     * @return 每天的00:00 - 00:05 是临界值
     */
    public static boolean isTodayCriticalValue() {
        long currentTime = System.currentTimeMillis();
        long differenceValue = (currentTime + TimeZone.getDefault().getRawOffset()) % (1000 * 3600 * 24);
        boolean isInCritical = differenceValue <= 1000 * 60 * 5;
        return isInCritical;
    }

    @Nullable
    public static Date formatStr(@Nullable String strDayOfWeek) {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dates = null;
        try {
            dates = sd.parse(strDayOfWeek);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dates;
    }

    public static String getAgo7Day(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();

        //过去七天
        c.setTime(date);
        c.add(Calendar.DATE, -7);
        Date d = c.getTime();
        String day = format.format(d);
        return day;

    }

    public static String getNext7Day(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        //过去七天
        c.setTime(date);
        c.add(Calendar.DATE, 7);
        Date d = c.getTime();
        String day = format.format(d);
        return day;
    }

    public static String getNextMonth(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 1);
        Date m = c.getTime();
        String mon = format.format(m);
        return mon;
    }

    public static String getAgoMonth(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String mon = format.format(m);
        return mon;

    }

    public static String getNextMonthEndDay(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 1);
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
        Date m = c.getTime();
        String mon = format.format(m);
        return mon;
    }

    public static String getAgoMonthEndDay(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, -1);
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
        Date m = c.getTime();
        String mon = format.format(m);
        return mon;

    }

    /**
     * 获取本周的开始时间
     *
     * @return yyyy-MM-dd HH:mm:ss  格式
     */
    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayStartTime(cal.getTime());
    }

    /**
     * 获取本周的结束时间
     *
     * @return yyyy-MM-dd HH:mm:ss  格式
     */
    public static Date getEndDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    /**
     * 获取本月的开始时间
     *
     * @return yyyy-MM-dd HH:mm:ss  格式
     */
    public static Date getBeginDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        return getDayStartTime(calendar.getTime());
    }

    /**
     * 获取某个日期的开始时间
     *
     * @param d
     * @return yyyy-MM-dd HH:mm:ss  格式
     */
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取某个日期的结束时间
     *
     * @param d
     * @return yyyy-MM-dd HH:mm:ss  格式
     */
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取今年是哪一年
     *
     * @return
     */
    public static Integer getNowYear() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return Integer.valueOf(gc.get(1));
    }

    /**
     * 获取本月是哪一月
     *
     * @return
     */
    public static int getNowMonth() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(2) + 1;
    }

    /**
     * 获取本月的结束时间
     * j
     *
     * @return yyyy-MM-dd HH:mm:ss  格式
     */
    public static Date getEndDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 1, day);
        return getDayEndTime(calendar.getTime());
    }


    /**
     * 获取指定月的前一月（年）或后一月（年）
     *
     * @param dateStr
     * @param addYear
     * @param addMonth
     * @param addDate
     * @return 输入的时期格式为yyyy-MM，输出的日期格式为yyyy-MM
     * @throws Exception
     */
//      System.out.println(getLastMonth("2011-06",0,-1,0));//2011-05
//             System.out.println(getLastMonth("2011-06",0,-6,0));//2010-12
//             System.out.println(getLastMonth("2011-06",-1,0,0));//2010-06
    public static String getLastMonth(String dateStr, int addYear, int addMonth, int addDate) throws Exception {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            Date sourceDate = sdf.parse(dateStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(sourceDate);
            cal.add(Calendar.YEAR, addYear);
            cal.add(Calendar.MONTH, addMonth);
            cal.add(Calendar.DATE, addDate);

            SimpleDateFormat returnSdf = new SimpleDateFormat("yyyy-MM");
            String dateTmp = returnSdf.format(cal.getTime());
            Date returnDate = returnSdf.parse(dateTmp);
            return dateTmp;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 获取某年某月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getStartMonthDate(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        return calendar.getTime();
    }

    /**
     * 获取某年某月的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getEndMonthDate(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(year, month - 1, day);
        return calendar.getTime();
    }

    /**
     * 获取两个日期的月数差
     *
     * @return
     */
    public static long getDifferMonth(Calendar fromCalendar, Calendar toCalendar) {
        int fromYear = fromCalendar.get(Calendar.YEAR);
        int toYear = toCalendar.get((Calendar.YEAR));
        if (fromYear == toYear) {
            return Math.abs(fromCalendar.get(Calendar.MONTH) - toCalendar.get(Calendar.MONTH));
        } else {
            int fromMonth = 12 - (fromCalendar.get(Calendar.MONTH) + 1);
            int toMonth = toCalendar.get(Calendar.MONTH) + 1;
            return Math.abs(toYear - fromYear - 1) * 12 + fromMonth + toMonth;
        }
    }

}
