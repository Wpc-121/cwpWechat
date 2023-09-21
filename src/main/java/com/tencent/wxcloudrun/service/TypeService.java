package com.tencent.wxcloudrun.service;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;

public interface TypeService {
    ApiResponse addType(JSONObject req);
    ApiResponse queryAllTypes(JSONObject req);
}
