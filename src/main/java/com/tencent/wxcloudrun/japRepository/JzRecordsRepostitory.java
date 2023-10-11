package com.tencent.wxcloudrun.japRepository;

import com.tencent.wxcloudrun.japEntity.JzRecords;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;

public interface JzRecordsRepostitory extends CrudRepository<JzRecords, Long> {
    List<JzRecords> findJzRecordsByRecOwneridOrderByRecBornTimeDesc(String openid);
    List<JzRecords> findJzRecordsByRecBornTimeAfterOrderByRecBornTimeDesc(String today);


    @Query(value = "select jr.rec_type,cast(sum(jr.rec_money) as char ) total from jz_records jr " +
            " where jr.rec_ownerid =?1 and (jr.rec_date > ?2" +
            " and jr.rec_date<?3)  group by jr.rec_type" ,nativeQuery = true)
    List<Map<String, Object>> queryRecordsGroupWithOpenid(String openid,String queryDate,String queryEndDate );

    @Query(value = "select jr.rec_type,cast(sum(jr.rec_money) as char )  total from jz_records jr " +
            "  group by jr.rec_type" ,nativeQuery = true)
    List<Map<String, Object>> queryRecordsGroupWithOutOpenid();

    @Query(value = "select jr.rec_date, " +
            " cast(sum(case jr.rec_type when '1' then jr .rec_money else 0 end) as char ) outMoney,\n" +
            " cast(sum(case jr.rec_type when '2' then jr .rec_money else 0 end) as char ) inMoney\n" +
            " from jz_records jr  where   rec_ownerid =?1 and (jr.rec_date > ?2 "  +
            "             and jr.rec_date<?3) " +
            " group by jr.rec_date order by jr.rec_date DESC " ,nativeQuery = true)
    List<Map<String,Object>> queryRecordsGroupByDay(String openId,String queryDate,String queryEndDate);

    @Query(value = "select * from jz_records jr left join \n" +
            " (SELECT jt.typeid ,jt.typename ,ji.jz_base64  FROM jz_types jt " +
            " left join jz_icons ji on jt.typeicon =ji.jz_iconid) jt on jr.rec_jz_type_id =jt.typeid \n" +
            " where rec_date =?1 and rec_ownerid =?2" +
            " order by jr.rec_date desc,jr.rec_born_time desc",nativeQuery = true)
    List<Map<String, Object>> queryDayRecordsByDay(String day,String openId);

    @Query(value = "select jr.rec_date, " +
            " cast(sum(case jr.rec_type when '1' then jr .rec_money else 0 end) as char ) outMoney,\n" +
            " cast(sum(case jr.rec_type when '2' then jr .rec_money else 0 end) as char ) inMoney\n" +
            " from jz_records jr  where   rec_ownerid =?1 and (jr.rec_date >= ?2 "  +
            "             and jr.rec_date<=?3) " +
            " group by jr.rec_date order by jr.rec_date DESC " ,nativeQuery = true)
    List<Map<String,Object>> queryRecordsGroupByWeekDay(String openId,String queryDate,String queryEndDate);

    @Query(value = "select SUBSTR( jr.rec_date,1,7) rec_date, " +
            " cast(sum(case jr.rec_type when '1' then jr .rec_money else 0 end) as char ) outMoney,\n" +
            " cast(sum(case jr.rec_type when '2' then jr .rec_money else 0 end) as char ) inMoney\n" +
            " from jz_records jr  where   rec_ownerid =?1 and (jr.rec_date >= ?2 "  +
            "             and jr.rec_date<=?3) " +
            " group by SUBSTR( jr.rec_date,1,7) " ,nativeQuery = true)
    List<Map<String,Object>> queryRecordsGroupByYear(String openId,String queryDate,String queryEndDate);

    @Query(value = "select   " +
            " ifnull(cast(sum(case jr.rec_type when '1' then jr .rec_money else 0 end) as char ), '0.00') outMoney,\n" +
            " ifnull(cast(sum(case jr.rec_type when '2' then jr .rec_money else 0 end) as char ), '0.00') inMoney\n" +
            " from jz_records jr  where   rec_ownerid =?1 and (jr.rec_date >= ?2 "  +
            "             and jr.rec_date<=?3) " ,nativeQuery = true)
    List<Map<String,Object>> queryRecordsSumByDate(String openId,String queryDate,String queryEndDate);

    @Query(value = "select a.outMoney,a.inMoney,jt.typename,jt.jz_base64  from (\n" +
            " select    jr.rec_jz_type_id ,cast(sum(case jr.rec_type when '1' then jr .rec_money else 0 end) as char ) outMoney,\n" +
            " cast(sum(case jr.rec_type when '2' then jr .rec_money else 0 end) as char ) inMoney\n" +
            " from jz_records jr where jr.rec_ownerid =?1 and (jr.rec_date >= ?2 \"  +\n" +
            "            \"             and jr.rec_date<=?3)  group by jr.rec_jz_type_id \n" +
            " ) a left join (\n" +
            " select jt.typeid ,jt.typename ,ji.jz_base64  FROM jz_types jt left join jz_icons ji on jt.typeicon =ji.jz_iconid \n" +
            " ) jt on a.rec_jz_type_id= jt.typeid  order by a.outMoney+0 desc ,a.inMoney+0 desc" ,nativeQuery = true)
    List<Map<String,Object>> queryRecordsSumByType(String openId,String queryDate,String queryEndDate);


}
