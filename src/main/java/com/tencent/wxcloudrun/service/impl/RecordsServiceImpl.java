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
        List<JzIcons> iconsList = jzIconsRepostitory.queryAllIcons();
        rsp.put("icons",iconsList);
        return ApiResponse.ok(rsp);
    }
}
