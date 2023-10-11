package com.tencent.wxcloudrun;

import com.alibaba.fastjson.JSONArray;
import com.tencent.wxcloudrun.japRepository.QueryBySqlRepository;
import com.tencent.wxcloudrun.utils.MyStringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class Tools {
      QueryBySqlRepository queryBySqlRepository;
    final SimpleDateFormat sdf  = new SimpleDateFormat("yyyyMMdd");
    final Logger logger;
    public Tools( @Autowired QueryBySqlRepository queryBySqlRepository) {
        this.logger = LoggerFactory.getLogger(Tools.class);
        this.queryBySqlRepository = queryBySqlRepository;
    }

    public  String getSeq(String seq, String startWith){
        logger.info("-----get into getseq : {}, start with:{}", seq,startWith);
        Object genid = queryBySqlRepository.genid();
        String today = sdf.format(new Date());
        String seqno = startWith+today+MyStringUtil.fillString(genid.toString(),10,'0');
        logger.info("----gen seqno is : {}",seqno);
        return seqno;
    }

    public   String getWeekStart(String pattern,String week) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.WEEK_OF_YEAR,Integer.valueOf(week));
//        c.setTime(c.getTime());
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            c.add(Calendar.DAY_OF_MONTH, -1);
        }
        c.add(Calendar.DATE, c.getFirstDayOfWeek() - c.get(Calendar.DAY_OF_WEEK) + 1);
        return new SimpleDateFormat(pattern).format(c.getTime());
    }

    public JSONArray getWeekDays(String week){
        JSONArray rsp = new JSONArray();
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
        String start = this.getWeekStart("yyyy/MM/dd",week);
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf1.parse(start));
            for (int i= 0;i<7;i++){
                rsp.add(sdf.format(c.getTime()));
                c.add(Calendar.DAY_OF_MONTH,1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  rsp;
    }

    public  JSONArray getMonthDays(String month){
        JSONArray rsp = new JSONArray();
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, Integer.valueOf(month)-1);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
            String d = sdf.format(c.getTime());
            for (int i= 0;i<Integer.valueOf(d);i++){
                rsp.add(i+1);
            }
        return  rsp;
    }

    public   String getWeekEnd(String pattern,String week) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.WEEK_OF_YEAR,Integer.valueOf(week));
        // 如果是周日直接返回
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            return new SimpleDateFormat(pattern).format(c.getTime());
        }
        //System.out.println(c.get(Calendar.DAY_OF_WEEK));
        c.add(Calendar.DATE, 7 - c.get(Calendar.DAY_OF_WEEK) + 1);
        return new SimpleDateFormat(pattern).format(c.getTime());
    }

    public   String getWeekOfNow() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(calendar.getTime());
        return calendar.get(Calendar.WEEK_OF_YEAR) + "";
    }

    public   String getMonthEnd(String pattern,String month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, Integer.valueOf(month)-1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date time = cal.getTime();
        return new SimpleDateFormat(pattern).format(time);
    }

    /**
     * 把数字转成BigDecimal类型
     *
     * @param str
     * @return
     */
    public   BigDecimal getDecimal(String str) {
        if (MyStringUtil.isNullOrEmpty(str)) {
            return new BigDecimal("0");
        }
        return new BigDecimal(str);
    }

    /**
     * 加法
     * @param str1
     * @param str2
     * @return
     */
    public   String addBigDecimal(String str1, String str2) {
        return getDecimal(str1).add(getDecimal(str2)).toPlainString();
    }

    /**
     * 减法
     * @param str1
     * @param str2
     * @return
     */
    public   String subtractBigDecimal(String str1, String str2) {
        return getDecimal(str1).subtract(getDecimal(str2)).toPlainString();
    }

    /**
     * 乘法,不保留小数
     * @param str1
     * @param str2
     * @param num 小数位数
     * @return
     */
    public     String multiplyBigDecimal(String str1, String str2) {
        return getDecimal(str1).multiply(getDecimal(str2)).toPlainString();
    }

    /**
     * 乘法,保留小数点
     * @param str1
     * @param str2
     * @param num 小数位数
     * @return
     */
    public   String multiplyBigDecimal(String str1, String str2,int num) {
        return getDecimal(str1).multiply(getDecimal(str2)).setScale(num,
                RoundingMode.HALF_UP).toPlainString();
    }

    /**
     * 比较两个数字大小
     * @param str1
     * @param str2
     * @return 相等返回0，前者大于后者返回1，小于返回-1
     */
    public   int compare(String str1, String str2) {
        return getDecimal(str1).compareTo(getDecimal(str2));
    }

    /**
     * 整数除法,保留小数点
     * @param str1
     * @param str2
     * @param num 小数位数
     * @return
     */
    public   String divideBigDecimal(String str1, String str2,int num) {
        return getDecimal(str1).divide(getDecimal(str2), num,
               RoundingMode.HALF_UP).toPlainString();
    }

    /**
     * 整数除法,不保留小数,不能为无线循环小数，否则报错
     * @param str1
     * @param str2
     * @param num 小数位数
     * @return
     */
    public   String divideBigDecimal(String str1, String str2) {
        return getDecimal(str1).divide(getDecimal(str2)).toPlainString();
    }
}
