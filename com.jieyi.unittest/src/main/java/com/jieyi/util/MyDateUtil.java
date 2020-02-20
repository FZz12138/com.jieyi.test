package com.jieyi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName MyDateUtil
 * @Description TODO
 * @Author Administrator
 * @Date 2019/11/8 0008 9:12
 * @Version 1.0
 **/
public class MyDateUtil {


    public static String getDate(String time,int days){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();
        Date date=null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        int day=calendar.get(Calendar.DATE);
        // 后一天为 +1   前一天 为-1
        calendar.set(Calendar.DATE,day+days);

        String nextDay = sdf.format(calendar.getTime());
        return nextDay;
    }

}
