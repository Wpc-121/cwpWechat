package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.Tools;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.japEntity.JzIcons;
import com.tencent.wxcloudrun.japEntity.JzRecords;
import com.tencent.wxcloudrun.japRepository.JzIconsRepostitory;
import com.tencent.wxcloudrun.japRepository.JzRecordsRepostitory;
import com.tencent.wxcloudrun.service.RecordsService;
import com.tencent.wxcloudrun.utils.MyStringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RecordsServiceImpl implements RecordsService {
    final JzRecordsRepostitory jzRecordsRepostitory;
    final Logger logger;
    final Tools tools;
    final SimpleDateFormat simpleDateFormat;

    @Autowired
    JzIconsRepostitory jzIconsRepostitory;
    public RecordsServiceImpl(@Autowired  JzRecordsRepostitory jzRecordsRepostitory, @Autowired Tools tools) {
        this.logger = LoggerFactory.getLogger(RecordsServiceImpl.class);
        this.jzRecordsRepostitory = jzRecordsRepostitory;
        this.tools = tools;
        this.simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    }

    @Override
    public ApiResponse addRecords(JSONObject req) {
        JSONObject rsp = new JSONObject();
        String openid = req.getString("openid");
        JzRecords jzRecords = JSONObject.toJavaObject(req, JzRecords.class);
        jzRecords.setRecOwnerid(openid);
        String recId = jzRecords.getRecId();
        String now = simpleDateFormat.format(new Date());
        jzRecords.setRecBornTime(now);
        if(MyStringUtil.isNullOrEmpty(recId)){
            logger.info("------recid is null or empty----add records---");
            recId = tools.getSeq("seq","REC");
            jzRecords.setRecId(recId);
            jzRecordsRepostitory.save(jzRecords);
        }else {
            jzRecords.setRecDel("0");
            logger.info("-----recid is not null ---update records----");
            jzRecordsRepostitory.save(jzRecords);
        }
        rsp.put("code","1111");
        rsp.put("msg","success");
        rsp.put("recId", recId);
        return ApiResponse.ok(rsp);
    }

    @Override
    public ApiResponse queryAllRecords(JSONObject req) {
        JSONObject rsp = new JSONObject();
        String openId = req.getString("openid");
        String queryDate = req.getString("queryDate");
        SimpleDateFormat daysdf = new SimpleDateFormat("yyyy/MM");
        String queryEndDate = "";
        if(MyStringUtil.isNullOrEmpty(queryDate)){
            queryDate = daysdf.format(new Date());
        }
        queryEndDate = queryDate + "/33";
        List<Map<String, Object>> groupRecords = new ArrayList<>();
        rsp.put("income", "0.00");
        rsp.put("outcome", "0.00");
        if(!MyStringUtil.isNullOrEmpty(openId)){
            logger.info("----openid is not null----{}", openId);
            groupRecords = jzRecordsRepostitory.queryRecordsGroupWithOpenid(openId,queryDate,queryEndDate);
        }else{
            logger.info("----openid is null ----");
            groupRecords = jzRecordsRepostitory.queryRecordsGroupWithOutOpenid();
        }
        List<Map<String,Object>> recordsByDayList  = jzRecordsRepostitory.queryRecordsGroupByDay(openId,queryDate,queryEndDate);
        JSONArray retRecs = new JSONArray();
        if(null!=recordsByDayList && recordsByDayList.size()>0){
            for(int i=0;i< recordsByDayList.size();i++){
                Map<String, Object> map = new HashMap<>(recordsByDayList.get(i));
                String day = (String) map.get("rec_date");
                List<Map<String,Object>> rows = jzRecordsRepostitory.queryDayRecordsByDay(day,openId);
                map.put("myrows", rows);
                retRecs.add(map);
            }
        }
        if(null!=groupRecords && groupRecords.size()>0){
            for(Map<String,Object> map : groupRecords){
                logger.info("------group records: {}",JSONObject.toJSONString(map));
                String jztype = (String) map.get("rec_type");
                String money = (String) map.get("total");
                if("2".equals(jztype)){
                    rsp.put("income" ,money);
                }else{
                    rsp.put("outcome" ,money);
                }
            }
        }
        rsp.put("records", retRecs);

        return ApiResponse.ok(rsp);
    }

    @Override
    public ApiResponse queryAllIcons(JSONObject req) {
        JSONObject rsp = new JSONObject();
        String openId = req.getString("openid");
        List<JzIcons> iconsList = jzIconsRepostitory.queryAllIcons(openId);
        rsp.put("icons",iconsList);
        return ApiResponse.ok(rsp);
    }

    @Override
    public ApiResponse queryDiagram(JSONObject req) {
        JSONObject rsp = new JSONObject();
        rsp.put("weeks","");
        rsp.put("months","");
        rsp.put("year","");
        String openid = req.getString("openid");
        String formatPar = "yyyy/MM/dd";
        SimpleDateFormat sdf = new SimpleDateFormat(formatPar);
        Calendar c = Calendar.getInstance();
        String[] fullDate = sdf.format(new Date()).split("/");
        String year = fullDate[0];
        String month = fullDate[1];
        //首先按周统计
        String week = tools.getWeekOfNow();
        JSONArray weeks = new JSONArray();
        for (int i=0;i<4;i++){
            JSONObject everyWeek = new JSONObject();
            String curWeek = (Integer.valueOf(week)-i)+"";
            JSONArray weekDays = tools.getWeekDays(curWeek);
            JSONArray weekDataOut = new JSONArray();
            JSONArray weekDataIn = new JSONArray();
            String totalOut = "0.00";
            String totalIn = "0.00";
            for(int j =0;j< weekDays.size();j++){
                weekDataOut.add("0.00");
                weekDataIn.add("0.00");
            }
            String weekStart = tools.getWeekStart(formatPar,curWeek);
            String weekEnd = tools.getWeekEnd(formatPar,curWeek);
            List<Map<String,Object>> recordsWeek = jzRecordsRepostitory.queryRecordsGroupByWeekDay(openid,weekStart,weekEnd);
            List<Map<String,Object>> recordsSum = jzRecordsRepostitory.queryRecordsSumByDate(openid,weekStart,weekEnd);
            if(null!=recordsSum && recordsSum.size()>0){
                Map<String,Object> map = recordsSum.get(0);
                totalIn = (String) map.get("inMoney");
                totalOut = (String) map.get("outMoney");
            }
            if(null!=recordsWeek && recordsWeek.size()>0){
                for(Map<String,Object> map: recordsWeek){
                    String day = (String) map.get("rec_date");
                    int in = weekDays.indexOf(day.substring(day.length()-2));
                    if(in !=-1){
                        String outMoney = (String) map.get("outMoney");
                        String inMoney = (String) map.get("inMoney");
                        weekDataOut.set(in, outMoney);
                        weekDataIn.set(in, inMoney);
                    }
                }
            }

            everyWeek.put("totalOut",totalOut);
            everyWeek.put("totalIn",totalIn);
            everyWeek.put("datax",weekDays);
            everyWeek.put("datayIn",weekDataIn);
            everyWeek.put("datayOut",weekDataOut);
            this.getRecordsRank(everyWeek,openid,weekStart,weekEnd);
            if(i==0){
                everyWeek.put("title","本周");
            }else{
                everyWeek.put("title",curWeek+"周");
            }
            weeks.add(everyWeek);
        }

        //按月统计
        JSONArray months = new JSONArray();
        for(int i=0; i<2;i++ ){
            JSONObject everyMonth = new JSONObject();
            String currMonth = (Integer.valueOf(month)-i)<10?("0"+(Integer.valueOf(month)-i)):(Integer.valueOf(month)-i)+"";
            String monthStart = year+"/"+currMonth;
            String monthEnd = tools.getMonthEnd(formatPar,currMonth);
            JSONArray monthdays = tools.getMonthDays(currMonth);
            JSONArray monthDayOut = new JSONArray();
            JSONArray monthDayIn = new JSONArray();
            String totalOut = "0.00";
            String totalIn = "0.00";
            for (int j=0;j< monthdays.size();j++){
                monthDayOut.add("0.00");
                monthDayIn.add("0.00");
            }
            List<Map<String,Object>> recordsWeek = jzRecordsRepostitory.queryRecordsGroupByWeekDay(openid,monthStart,monthEnd);
            List<Map<String,Object>> recordsSum = jzRecordsRepostitory.queryRecordsSumByDate(openid,monthStart,monthEnd);
            if(null!=recordsSum && recordsSum.size()>0){
                Map<String,Object> map = recordsSum.get(0);
                totalIn = (String) map.get("inMoney");
                totalOut = (String) map.get("outMoney");
            }
            if(null!=recordsWeek && recordsWeek.size()>0){
                for(Map<String,Object> map: recordsWeek){
                    String day = (String) map.get("rec_date");
                    int in = monthdays.indexOf(day.substring(day.length()-2));
                    if(in !=-1){
                        String outMoney = (String) map.get("outMoney");
                        String inMoney = (String) map.get("inMoney");
                        monthDayOut.set(in, outMoney);
                        monthDayIn.set(in, inMoney);
                    }
                }
            }
            everyMonth.put("totalOut", totalOut);
            everyMonth.put("totalIn", totalIn);
            everyMonth.put("datax", monthdays);
            everyMonth.put("datayIn", monthDayIn);
            everyMonth.put("datayOut", monthDayOut);
            everyMonth.put("title", currMonth+"月");
            this.getRecordsRank(everyMonth,openid,monthStart,monthEnd);
            months.add(everyMonth);
        }
        //按年统计
        JSONObject years = new JSONObject();
        JSONArray yearDatax = new JSONArray();
        JSONArray yearDatayOut = new JSONArray();
        JSONArray yearDatayIn = new JSONArray();
        String totalOut = "0.00";
        String totalIn = "0.00";
        for (int i=0;i<12;i++){
            yearDatax.add((i+1));
            yearDatayOut.add("0.00");
            yearDatayIn.add("0.00");
        }
        List<Map<String,Object>> recordsYear = jzRecordsRepostitory.queryRecordsGroupByYear(openid,year,year+"/13");
        List<Map<String,Object>> recordsSum = jzRecordsRepostitory.queryRecordsSumByDate(openid,year,year+"/13");
        if(null!=recordsSum && recordsSum.size()>0){
            Map<String,Object> map = recordsSum.get(0);
            totalIn = (String) map.get("inMoney");
            totalOut = (String) map.get("outMoney");
        }
        if(null!=recordsYear && recordsYear.size()>0){
            for(Map<String,Object> map: recordsYear){
                String day = (String) map.get("rec_date");
                int in = yearDatax.indexOf(Integer.valueOf(day.substring(day.length()-2)));
                if(in !=-1){
                    String outMoney = (String) map.get("outMoney");
                    String inMoney = (String) map.get("inMoney");
                    yearDatayOut.set(in, outMoney);
                    yearDatayIn.set(in, inMoney);
                }
            }
        }
        years.put("totalIn", totalIn);
        years.put("totalOut", totalOut);
        years.put("datax", yearDatax);
        years.put("datayIn", yearDatayIn);
        years.put("datayOut", yearDatayOut);
        years.put("title", year+"年");
        this.getRecordsRank(years,openid,year,year+"/13");

        JSONArray yearss = new JSONArray();
        yearss.add(years);
        rsp.put("weeks",weeks);
        rsp.put("months",months);
        rsp.put("year",yearss);
        return ApiResponse.ok(rsp);
    }

    @Override
    public ApiResponse addIcon(JSONObject req) {
        String openid = req.getString("openid");
        String iconurl = req.getString("iconurl");
        String iconname = req.getString("iconname");
        String fileId = req.getString("fileId");
        String iconid = tools.getSeq("seq","ICON");
        String fileUrl = tools.getDownUrlFromWechatCloud(fileId);
        logger.info("----file down url is -"+fileUrl);
        String iconbase64 = tools.imageUrlToBase64(fileUrl);
        JzIcons jzIcons = new JzIcons();
        jzIcons.setJzIconid(iconid);
        jzIcons.setJzIconOwner(openid);
        jzIcons.setJzIconurl(iconname);
        jzIcons.setJzBase64(iconbase64);
        JzIcons jzIcons1 = jzIconsRepostitory.save(jzIcons);
        return ApiResponse.ok(jzIcons1);
    }

    public  void getRecordsRank(JSONObject req,String openid, String weekStart,String weekEnd){
        logger.info("----getRecordsRank----"+weekEnd+"-----"+weekStart);
        List<Map<String, Object>> recordsRank = jzRecordsRepostitory.queryRecordsSumByType(openid,weekStart,weekEnd);
        JSONArray rankOut = new JSONArray();
        JSONArray rankIn = new JSONArray();
        if(null!=recordsRank&& recordsRank.size()>0){
            String totalIn = req.getString("totalIn");
            String totalOut = req.getString("totalOut");
            for (Map<String,Object> map: recordsRank){
                    Map<String,Object> newMap = new HashMap<>(map);
                    String outMoney = (String) map.get("outMoney");
                    String inMoney = (String) map.get("inMoney");
                    logger.info("---outMoney--"+outMoney+"--inMoney--"+inMoney);
                    if(!"0".equals(outMoney)){
                        logger.info("-----out money----");
                        String percent = tools.multiplyBigDecimal(tools.divideBigDecimal(outMoney,totalOut,2),"100");
                        newMap.put("percent",percent);
                        rankOut.add(newMap);
                    }
                    if(!"0".equals(inMoney)){
                        logger.info("-----in money----");
                        String percent = tools.multiplyBigDecimal(tools.divideBigDecimal(inMoney,totalIn,2),"100");
                        newMap.put("percent",percent);
                        rankIn.add(newMap);
                    }
            }
        }
        req.put("rankIn",rankIn);
        req.put("rankOut",rankOut);
    }
}
