package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.Tools;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.japEntity.JzTags;
import com.tencent.wxcloudrun.japEntity.JzTypes;
import com.tencent.wxcloudrun.japRepository.JzTagsRepository;
import com.tencent.wxcloudrun.japRepository.JzTypeRepostitory;
import com.tencent.wxcloudrun.service.TypeService;
import com.tencent.wxcloudrun.utils.MyStringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class TypeServiceImpl implements TypeService {
    final Logger logger;
    final JzTypeRepostitory jzTypeRepostitory;
    @Autowired
    Tools tools;
    @Autowired
    JzTagsRepository jzTagsRepository;
    public TypeServiceImpl(@Autowired JzTypeRepostitory jzTypeRepostitory) {
        this.logger = LoggerFactory.getLogger(TypeServiceImpl.class);
        this.jzTypeRepostitory = jzTypeRepostitory;
    }


    @Override
    public ApiResponse addType(JSONObject req) {
        String addtype = req.getString("typefather");
        String openid = req.getString("openid");
        JzTypes jzTypes = new JzTypes();
        jzTypes = JSONObject.toJavaObject(req, JzTypes.class);
        jzTypes.setTypeowner(openid);
        String typeId = jzTypes.getTypeid();
        if(MyStringUtil.isNullOrEmpty(typeId)){
            typeId = tools.getSeq("seq","TP");
            jzTypes.setTypeid(typeId);
        }
        if("1".equals(addtype)){
            logger.info("-----addtype is {}----","father "+ addtype);
        }else {
            logger.info("-----addtype is {}----","son "+ addtype);

        }
        JzTypes save = jzTypeRepostitory.save(jzTypes);
        return ApiResponse.ok();
    }

    @Override
    public ApiResponse queryAllTypes(JSONObject req) {
        String openid = req.getString("openid");
        List<Map<String,Object>> jzTypes = jzTypeRepostitory.queryAllTypesAndIcons("1",openid);
        List<Map<String,Object>> jzInTypes = jzTypeRepostitory.queryAllTypesAndIcons("2",openid);
        List<Map<String, Object>> maps = jzTagsRepository.querytagsByOpenid(openid);
        JSONObject rsp = new JSONObject();
        rsp.put("outTypes", jzTypes);
        rsp.put("inTypes", jzInTypes);
        rsp.put("tags", maps);
        return ApiResponse.ok(rsp);
    }
}
