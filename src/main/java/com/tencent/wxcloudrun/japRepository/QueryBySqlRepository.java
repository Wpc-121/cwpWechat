package com.tencent.wxcloudrun.japRepository;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.japEntity.JzSeq;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

public interface QueryBySqlRepository extends CrudRepository<JzSeq, Long> {
    @Query(value = "select TRIM( nextval(\"seq\")) seq  ", nativeQuery = true)
    Object genid();
}
