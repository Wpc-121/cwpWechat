package com.tencent.wxcloudrun.japRepository;

import com.tencent.wxcloudrun.japEntity.JzIcons;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;

public interface JzIconsRepostitory extends CrudRepository<JzIcons, Long> {
    @Query(value = "SELECT ji.jz_iconid ,ji .jz_iconurl ,ji .jz_base64,jz_icon_owner ,jz_time " +
            "  FROM jz_icons ji where " +
            " (ji.jz_icon_owner is null or ji.jz_icon_owner =?1) order by ji .jz_iconid "
            ,nativeQuery = true)
    List<JzIcons> queryAllIcons(String openid);
}
