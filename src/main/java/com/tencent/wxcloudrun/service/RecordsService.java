package com.tencent.wxcloudrun.service;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;

public interface RecordsService {
    ApiResponse addRecords(JSONObject req);
    ApiResponse queryAllRecords(JSONObject req);
    ApiResponse queryAllIcons(JSONObject req);
}
