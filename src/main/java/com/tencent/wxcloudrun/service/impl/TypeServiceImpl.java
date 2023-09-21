package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.Tools;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.japEntity.JzTypes;
import com.tencent.wxcloudrun.japRepository.JzTypeRepostitory;
import com.tencent.wxcloudrun.service.TypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TypeServiceImpl implements TypeService {
    final Logger logger;
    final JzTypeRepostitory jzTypeRepostitory;
    @Autowired
    Tools tools;
    public TypeServiceImpl(@Autowired JzTypeRepostitory jzTypeRepostitory) {
        this.logger = LoggerFactory.getLogger(TypeServiceImpl.class);
        this.jzTypeRepostitory = jzTypeRepostitory;
    }


    @Override
    public ApiResponse addType(JSONObject req) {
        String addtype = req.getString("typefather");
        JzTypes jzTypes = new JzTypes();
        String typeId = tools.getSeq("seq","TP");
        jzTypes = JSONObject.toJavaObject(req, JzTypes.class);
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
        return null;
    }
}
