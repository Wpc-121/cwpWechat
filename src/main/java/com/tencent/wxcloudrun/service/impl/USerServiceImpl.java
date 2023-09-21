package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.Tools;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.japEntity.JzUsers;
import com.tencent.wxcloudrun.japRepository.jzUserRepostitory;
import com.tencent.wxcloudrun.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class USerServiceImpl implements UserService {
    final Logger logger;
    final  jzUserRepostitory jzUserRepostitory;
    public USerServiceImpl(@Autowired jzUserRepostitory jzUserRepostitory) {
        this.logger = LoggerFactory.getLogger(USerServiceImpl.class);
        this.jzUserRepostitory = jzUserRepostitory;
    }

    @Autowired
    Tools tools;
    @Override
    public ApiResponse userlogin(JSONObject req) {
        logger.info("--userLogin req is {}----", req);
        String openid = req.getString("openid");
        JzUsers jzUsers = new JzUsers();
        jzUsers.setUseropenid(openid);
        jzUserRepostitory.save(jzUsers);
        String seqno = tools.getSeq("seq", "REQ");
        req.put("seqno", seqno);
        return ApiResponse.ok(req);
    }
}
