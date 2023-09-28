package com.tencent.wxcloudrun.aops;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.annos.MetCost;
import org.apache.ibatis.reflection.ArrayUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MonitorMetCostTime {

    private static String seqno = "";
    private static final Logger log = LoggerFactory.getLogger(MonitorMetCostTime.class);
    @Before("@annotation(metCost)")
    public void metBefore( MetCost metCost){
        String name = metCost.value();
        log.info("before  {} method   start---",name);
    }

    @Around("@annotation(metCost)")
    public Object metCost(ProceedingJoinPoint joinPoint, MetCost metCost) throws Throwable {
        String name = metCost.value();
        long start = System.currentTimeMillis();
        Object [] args = joinPoint.getArgs();
        log.info("-------jsoninPoint---args:{},---args.size----{}",ArrayUtil.toString(args),args.length);
        log.info("------seqno is-------{}",seqno);
        Object ret = joinPoint.proceed();
        try {
            return joinPoint.proceed();
        }finally {
            long end = System.currentTimeMillis();
            log.info("-----rspis: "+ JSONObject.toJSONString(ret));
            log.info("method-----"+name+" cost milliseconds are: "+(end-start));
        }
    }

}
