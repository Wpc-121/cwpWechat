package com.tencent.wxcloudrun.service;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;

public interface RecordsService {
    ApiResponse addRecords(JSONObject req);
    ApiResponse queryAllRecords(JSONObject req);
    ApiResponse queryAllIcons(JSONObject req);
    ApiResponse queryDiagram(JSONObject req);
    ApiResponse addIcon(JSONObject req);
    ApiResponse getAnId(JSONObject req);

    ApiResponse addLifeShow(JSONObject req);
    ApiResponse getTangshi(JSONObject req);

    ApiResponse sendMsg(JSONObject req);
}
