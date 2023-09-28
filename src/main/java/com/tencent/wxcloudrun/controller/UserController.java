package com.tencent.wxcloudrun.controller;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.annos.MetCost;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.service.RecordsService;
import com.tencent.wxcloudrun.service.TypeService;
import com.tencent.wxcloudrun.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cwp")
public class UserController {
    final UserService userService;
    final RecordsService recordsService;
    final TypeService typeService;
    final Logger logger;

    public UserController(@Autowired UserService userService, @Autowired RecordsService recordsService,
                          @Autowired TypeService typeService) {
        this.logger = LoggerFactory.getLogger(UserController.class);
        this.userService = userService;
        this.recordsService = recordsService;
        this.typeService = typeService;
    }

    @MetCost("userLogin")
    @PostMapping("/userLogin")
    public ApiResponse userLogin(@RequestBody JSONObject req){
        return userService.userlogin(req);
    }

    @MetCost("recordEdit")
    @PostMapping("/recordEdit")
    public ApiResponse editRecord(@RequestBody JSONObject req){
        return recordsService.addRecords(req);
    }

    @MetCost("queryRecords")
    @PostMapping("/queryRecords")
    public ApiResponse queryAllRecord(@RequestBody JSONObject req){
        return recordsService.queryAllRecords(req);
    }

    @MetCost("addType")
    @PostMapping("/addType")
    public ApiResponse addType(@RequestBody JSONObject req){
        return typeService.addType(req);
    }

    @MetCost("queryTypes")
        @PostMapping("/queryTypes")
    public ApiResponse queryTypes(@RequestBody JSONObject req){
        return typeService.queryAllTypes(req);
    }

    @MetCost("queryIcons")
    @PostMapping("/queryIcons")
    public ApiResponse queryIcons(@RequestBody JSONObject req){
        return recordsService.queryAllIcons(req);
    }
}
