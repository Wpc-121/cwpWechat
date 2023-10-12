package com.tencent.wxcloudrun.service;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;

public interface UserService {
    ApiResponse userlogin(JSONObject req);
    ApiResponse userAdd(JSONObject req);
    ApiResponse userQuery(JSONObject req);
}
