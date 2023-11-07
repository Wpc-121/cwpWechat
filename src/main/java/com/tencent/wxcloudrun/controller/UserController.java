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

    @MetCost("recvMsg")
    @PostMapping("/recvMsg")
    public String recvMsg(@RequestBody JSONObject req){
        logger.info("------recvMsg---req： "+ req);
        return "success";
    }

    @MetCost(("sendMsg"))
    @PostMapping("/sendMsg")
    public ApiResponse sendMsg(@RequestBody JSONObject req){
        return recordsService.sendMsg(req);
    }

    @MetCost("userLogin")
    @PostMapping("/userLogin")
    public ApiResponse userLogin(@RequestBody JSONObject req){
        return userService.userlogin(req);
    }

    @MetCost("userAdd")
    @PostMapping("/userAdd")
    public ApiResponse userAdd(@RequestBody JSONObject req){
        return userService.userAdd(req);
    }

    @MetCost("editTag")
    @PostMapping("/editTag")
    public ApiResponse editTag(@RequestBody JSONObject req){
        return userService.editTags(req);
    }
    @MetCost("queryTags")
    @PostMapping("/queryTags")
    public ApiResponse queryTags(@RequestBody JSONObject req){
        return userService.queryTags(req);
    }

    @MetCost("userQuery")
    @PostMapping("/userQuery")
    public ApiResponse userQuery(@RequestBody JSONObject req){
        return userService.userQuery(req);
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

    @MetCost("queryDiagram")
    @PostMapping("/queryDiagram")
    public ApiResponse queryDiagram(@RequestBody JSONObject req){
        return recordsService.queryDiagram(req);
    }
    @MetCost("addIcon")
    @PostMapping("/addIcon")
    public ApiResponse addIcon(@RequestBody JSONObject req){
        return recordsService.addIcon(req);
    }

    @MetCost("getAnId")
    @PostMapping("/getAnId")
    public ApiResponse getAnId(@RequestBody JSONObject req){
        return recordsService.getAnId(req);
    }


    @MetCost("addLifeShow")
    @PostMapping("/addLifeShow")
    public ApiResponse addLifeShow(@RequestBody JSONObject req){
        return recordsService.addLifeShow(req);
    }
    @MetCost("getTangshi")
    @PostMapping("/getTangshi")
    public ApiResponse getTangshi(@RequestBody JSONObject req){
        return recordsService.getTangshi(req);
    }
    @MetCost("queryLifeShow")
    @PostMapping("/queryLifeShow")
    public ApiResponse queryLifeShow(@RequestBody JSONObject req){
        return recordsService.queryLifeShow(req);
    }

    @MetCost("queryRecsByType")
    @PostMapping("/queryRecsByType")
    public ApiResponse queryRecsByType(@RequestBody JSONObject req){
        return recordsService.queryRecsByType(req);
    }
}
