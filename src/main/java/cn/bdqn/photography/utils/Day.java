package cn.bdqn.photography.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class Day {

    //获取当前时间一个月后时间
    public String nextMonth() {
        Date date = new Date();
        int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));//取到年份值
        int month = Integer.parseInt(new SimpleDateFormat("MM").format(date)) + 1;//取到鱼粉值
        int day = Integer.parseInt(new SimpleDateFormat("dd").format(date));//取到天值
        if (month == 0) {
            year -= 1;
            month = 12;
        } else if (day > 28) {
            if (month == 2) {
                if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
                    day = 29;
                } else day = 28;
            } else if ((month == 4 || month == 6 || month == 9 || month == 11) && day == 31) {
                day = 30;
            }
        }
        String y = year + "";
        String m = "";
        String d = "";
        if (month < 10) m = "0" + month;
        else m = month + "";
        if (day < 10) d = "0" + day;
        else d = day + "";

        return y + "-" + m + "-" + d;
    }

}
