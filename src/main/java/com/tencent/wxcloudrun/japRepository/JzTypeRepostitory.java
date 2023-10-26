package com.tencent.wxcloudrun.japRepository;

import com.tencent.wxcloudrun.japEntity.JzTypes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;

public interface JzTypeRepostitory extends CrudRepository<JzTypes,Long> {
    List<JzTypes> queryAllByType(String type);



    @Query(value = "SELECT jt.typeid ,jt.typename ,jt.type,ji.jz_base64,ji.jz_icon_owner,jt.typeicon  FROM jz_types jt " +
            " left join jz_icons ji on jt.typeicon =ji.jz_iconid where jt.`type` =?1 and " +
            " (jt.typeowner is null or jt.typeowner =?2)"
            ,nativeQuery = true)
    List<Map<String,Object>> queryAllTypesAndIcons(String type,String openid);
}
