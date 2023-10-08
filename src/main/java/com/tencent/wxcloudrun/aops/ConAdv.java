package com.tencent.wxcloudrun.aops;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.utils.MyStringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

@RestControllerAdvice
public class ConAdv implements RequestBodyAdvice {
    private static final Logger log = LoggerFactory.getLogger(ConAdv.class);
    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        log.info("---"+methodParameter.getParameterName());
        log.info("---"+methodParameter.getMethodAnnotation(RequestMapping.class).value());
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException {
        log.info(">>>>>beforeBodyRead-------");

        byte[] bytes=new byte[httpInputMessage.getBody().available()];
        httpInputMessage.getBody().read(bytes);
        String reqType = type.getTypeName();
        log.info(type.getTypeName());
        String reqBod = new String(bytes);
        if("com.alibaba.fastjson.JSONObject".equals(reqType)){
            log.info("----req body is json object---");
            String s=new String(bytes);
            JSONObject reqBody = JSONObject.parseObject(s);
            JSONObject headers = JSONObject.parseObject(JSONObject.toJSONString(httpInputMessage.getHeaders()));
            String openid = headers.getString("x-wx-openid");
            if(MyStringUtil.isNullOrEmpty(openid)){
                log.info("---openid is null ----");
                openid = "oW8cq5N9DtR6EGTGzcHu6KXKlW8U";
            }
            reqBody.put("openid",openid);
            reqBod = JSONObject.toJSONString(reqBody);
        }
        log.info("---headers---"+JSONObject.toJSONString(httpInputMessage.getHeaders()));

        String finalReqBod = reqBod;
        return new HttpInputMessage() {
            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream(finalReqBod.getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                return httpInputMessage.getHeaders();
            }
        };

    }

    @Override
    public Object afterBodyRead(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        log.info(">>>>>afterbody-------"+JSONObject.toJSONString(o));
        return o;
    }

    @Override
    public Object handleEmptyBody(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }
    @ExceptionHandler
    public ApiResponse gloablExceptionHandler(Exception e){
        JSONObject body = new JSONObject();
        body.put("retmsg",e.getMessage());
        return ApiResponse.error(e.getMessage());
    }
}
