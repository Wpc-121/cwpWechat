package com.tencent.wxcloudrun.japRepository;

import com.tencent.wxcloudrun.japEntity.JzTags;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;

public interface JzTagsRepository extends CrudRepository<JzTags,Long> {
    List<JzTags> findAllByTagOwneridOrderByTagId(String openid);

    @Query(value = "select jt.tag_id ,jt.tag_name as text, jt.tag_id as value from " +
            " jz_tags jt where jt.tag_ownerid =?1 " ,nativeQuery = true)
    List<Map<String, Object>> querytagsByOpenid(String openid);
}
