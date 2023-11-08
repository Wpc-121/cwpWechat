package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.Tools;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.japEntity.JzTags;
import com.tencent.wxcloudrun.japEntity.JzUsers;
import com.tencent.wxcloudrun.japRepository.JzTagsRepository;
import com.tencent.wxcloudrun.japRepository.jzUserRepostitory;
import com.tencent.wxcloudrun.service.UserService;
import com.tencent.wxcloudrun.utils.MyStringUtil;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class USerServiceImpl implements UserService {
    final Logger logger;
    final  jzUserRepostitory jzUserRepostitory;
    public USerServiceImpl(@Autowired jzUserRepostitory jzUserRepostitory) {
        this.logger = LoggerFactory.getLogger(USerServiceImpl.class);
        this.jzUserRepostitory = jzUserRepostitory;
    }

    @Autowired
    JzTagsRepository jzTagsRepository;

    @Value("${cwp.wechatCloudEnvId}")
    private String envId;

    @Autowired
    Tools tools;



    @Override
    public ApiResponse userlogin(JSONObject req) {
        logger.info("--userLogin req is {}----", req);
        String openid = req.getString("openid");
        JzUsers jzUsers = new JzUsers();
        jzUsers.setUseropenid(openid);
        JzUsers byUseropenid = jzUserRepostitory.findByUseropenid(openid);
        String seqno = tools.getSeq("seq", "REQ");
        req.put("seqno", seqno);
        req.put("userInfo",byUseropenid);
        return ApiResponse.ok(req);
    }

    @Override
    public ApiResponse userAdd(JSONObject req) {
        String openid = req.getString("openid");
        String nickname = req.getString("nickname");
        String avaurl = req.getString("ava");
        String fileId = req.getString("fileId");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        String fileUrl = tools.getDownUrlFromWechatCloud(fileId);
//        logger.info("----file down url is -"+fileUrl);
//        String headImgBase64 = tools.imageUrlToBase64(fileUrl);
        JzUsers jzUsers = new JzUsers();
        jzUsers.setUseropenid(openid);
        jzUsers.setUsername(nickname);
        jzUsers.setUserregtime(sdf.format(new Date()));
        jzUsers.setUserlatesttime(sdf.format(new Date()));
        jzUsers.setUserava(fileId);
        JzUsers save = jzUserRepostitory.save(jzUsers);
        return ApiResponse.ok(save);
    }

    @Override
    public ApiResponse userQuery(JSONObject req) {
        String openid = req.getString("openid");
        JSONObject rsp = new JSONObject();
        JzUsers byUseropenid = jzUserRepostitory.findByUseropenid(openid);
        if(byUseropenid == null){
            logger.info("-----未查询到用户信息-----");
            rsp.put("hasUser","0");
        }else{
            rsp.put("hasUser","1");
            rsp.put("user",byUseropenid);
        }
        return ApiResponse.ok(rsp);
    }

    @Override
    public ApiResponse editTags(JSONObject req) {
        String openid = req.getString("openid");
        String tagId = req.getString("tagid");
        String name = req.getString("name");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        if(MyStringUtil.isNullOrEmpty(tagId)){
            tagId = tools.getSeq("seq","TAG");
        }
        JzTags jzTags = new JzTags();
        jzTags.setTagId(tagId);
        jzTags.setTagName(name);
        jzTags.setTagOwnerid(openid);
        jzTags.setTagTime(sdf.format(new Date()));
        JzTags save = jzTagsRepository.save(jzTags);
        return ApiResponse.ok(save);
    }

    @Override
    public ApiResponse queryTags(JSONObject req) {
        String openid = req.getString("openid");
        List<Map<String, Object>> allByTagOwneridOrderByTagId = jzTagsRepository.querytagsByOpenid(openid);
        return ApiResponse.ok(allByTagOwneridOrderByTagId);
    }
}
