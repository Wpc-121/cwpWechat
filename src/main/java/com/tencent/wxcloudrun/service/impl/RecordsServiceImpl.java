package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.Tools;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.japEntity.JzRecords;
import com.tencent.wxcloudrun.japRepository.JzRecordsRepostitory;
import com.tencent.wxcloudrun.service.RecordsService;
import com.tencent.wxcloudrun.utils.MyStringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class RecordsServiceImpl implements RecordsService {
    final JzRecordsRepostitory jzRecordsRepostitory;
    final Logger logger;
    final Tools tools;
    final SimpleDateFormat simpleDateFormat;

    public RecordsServiceImpl(@Autowired  JzRecordsRepostitory jzRecordsRepostitory, @Autowired Tools tools) {
        this.logger = LoggerFactory.getLogger(RecordsServiceImpl.class);
        this.jzRecordsRepostitory = jzRecordsRepostitory;
        this.tools = tools;
        this.simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    }

    @Override
    public ApiResponse addRecords(JSONObject req) {
        JSONObject rsp = new JSONObject();
        JzRecords jzRecords = JSONObject.toJavaObject(req, JzRecords.class);
        String recId = jzRecords.getRecId();
        String now = simpleDateFormat.format(new Date());
        jzRecords.setRecBornTime(now);
        if(MyStringUtil.isNullOrEmpty(recId)){
            logger.info("------recid is null or empty----add records---");
            recId = tools.getSeq("seq","REC");
            jzRecords.setRecId(recId);
            jzRecordsRepostitory.save(jzRecords);
        }else {
            logger.info("-----recid is not null ---update records----");
            jzRecordsRepostitory.save(jzRecords);
        }
        rsp.put("code","1111");
        rsp.put("msg","success");
        rsp.put("recId", recId);
        return ApiResponse.ok(rsp);
    }
}
