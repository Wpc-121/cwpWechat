package com.tencent.wxcloudrun.controller;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.japEntity.JzUsers;
import com.tencent.wxcloudrun.japRepository.jzUserRepostitory;
import com.tencent.wxcloudrun.service.RecordsService;
import com.tencent.wxcloudrun.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/cwp")
public class UserController {
    final UserService userService;
    final RecordsService recordsService;
    final Logger logger;

    public UserController(@Autowired UserService userService, @Autowired RecordsService recordsService) {
        this.logger = LoggerFactory.getLogger(UserController.class);
        this.userService = userService;
        this.recordsService = recordsService;
    }

    @PostMapping("/userLogin")
    public ApiResponse userLogin(@RequestBody JSONObject req){
        return userService.userlogin(req);
    }

    @PostMapping("/recordEdit")
    public ApiResponse editRecord(@RequestBody JSONObject req){
        return recordsService.addRecords(req);
    }
}
