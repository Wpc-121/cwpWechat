package com.tencent.wxcloudrun;

import com.tencent.wxcloudrun.japRepository.QueryBySqlRepository;
import com.tencent.wxcloudrun.utils.MyStringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Tools {
      QueryBySqlRepository queryBySqlRepository;
    final SimpleDateFormat sdf  = new SimpleDateFormat("yyyyMMdd");
    final Logger logger;
    public Tools( @Autowired QueryBySqlRepository queryBySqlRepository) {
        this.logger = LoggerFactory.getLogger(Tools.class);
        this.queryBySqlRepository = queryBySqlRepository;
    }

    public  String getSeq(String seq, String startWith){
        logger.info("-----get into getseq : {}, start with:{}", seq,startWith);
        Object genid = queryBySqlRepository.genid();
        String today = sdf.format(new Date());
        String seqno = startWith+today+MyStringUtil.fillString(genid.toString(),10,'0');
        logger.info("----gen seqno is : {}",seqno);
        return seqno;
    }
}
