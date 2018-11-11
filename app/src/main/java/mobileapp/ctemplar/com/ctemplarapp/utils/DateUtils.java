package mobileapp.ctemplar.com.ctemplarapp.utils;

import android.text.TextUtils;
import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import mobileapp.ctemplar.com.ctemplarapp.CTemplarApp;
import mobileapp.ctemplar.com.ctemplarapp.R;

public class DateUtils {

    private final static String FORMAT_SOURCE_DATE = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'";
    private final static String FORMAT_24_HOURS = "HH:mm";
    private final static String FORMAT_12_HOURS = "h:mm a";
    private final static String FORMAT_MONTH_SHORT_USA = "MMM d";
    private final static String FORMAT_MONTH_SHORT_EU = "d MMM";

    private final static int TIME_MILLIS = 1000;
    private final static int TIME_SECONDS = 60;
    private final static int TIME_MINUTES = 60;
    private final static int TIME_HOURS = 24;
    private final static int TIME_DAYS_IN_YEAR = 365;
    private final static int TIME_SECONDS_IN_YEAR = TIME_MILLIS * TIME_SECONDS * TIME_MINUTES * TIME_HOURS * TIME_DAYS_IN_YEAR;

    public static boolean is24HourFormatTime() {
        return DateFormat.is24HourFormat(CTemplarApp.getInstance());
    }

    // example: 2018-10-17T12:27:05.545003Z
    public static String formatMessageDate(String date) {

        if(!TextUtils.isEmpty(date)) {
            Calendar targetCalendar = Calendar.getInstance();
            SimpleDateFormat targetDateFormat = new SimpleDateFormat(FORMAT_SOURCE_DATE);
            Date targetDate = new Date();

            Calendar currentCalendar = Calendar.getInstance();
            SimpleDateFormat resultFormat;
            TimeZone currentTimeZone = currentCalendar.getTimeZone();

            try {
                targetDate = targetDateFormat.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            targetCalendar.setTimeZone(currentTimeZone);
            targetCalendar.setTime(targetDate);
            long timeDifference = currentCalendar.getTimeInMillis() - targetCalendar.getTimeInMillis();

            // case less a day
            if(timeDifference < (TIME_MILLIS*TIME_SECONDS*TIME_MINUTES*TIME_HOURS)) {
                if(is24HourFormatTime()) {
                    resultFormat = new SimpleDateFormat(FORMAT_24_HOURS);
                } else {
                    resultFormat = new SimpleDateFormat(FORMAT_12_HOURS);
                }
                return resultFormat.format(targetDate);
            }

            // check if yesterday and earlier but no more than 1 year
            if((TIME_MILLIS*TIME_SECONDS*TIME_MINUTES*TIME_HOURS) <timeDifference &&
                    timeDifference<TIME_SECONDS_IN_YEAR) {

                if(is24HourFormatTime()) {
                    resultFormat = new SimpleDateFormat(FORMAT_MONTH_SHORT_EU);
                } else {
                    resultFormat = new SimpleDateFormat(FORMAT_MONTH_SHORT_USA);
                }

                return resultFormat.format(targetDate);
            }

            int years = (int)Math.floor(timeDifference / TIME_SECONDS_IN_YEAR);
            if(years == 1){
                return CTemplarApp.getInstance().getResources().getString(R.string.year_single);
            } else if(years > 1) {
                return CTemplarApp.getInstance().getResources().getString(R.string.year_multiple, years);
            }
        }

        return "Date";
    }
}
