package com.tencent.wxcloudrun.japRepository;

import com.tencent.wxcloudrun.japEntity.JzLifeShow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Map;

public interface JzLifeShowRepostitory extends CrudRepository<JzLifeShow , Long> {

    @Query(value = " select jls.*,ju.username ,ju.userava  from jz_life_show jls left join  jz_users ju" +
            " on jls .life_show_ownerid =ju.useropenid  where jls .life_show_see ='1' OR " +
            "(jls.life_show_see='0' and jls.life_show_ownerid=?1)" +
            " order by jls .life_show_time DESC   ", countQuery = "select count(*) from (  " +
            "             select jls.*,ju.username ,ju.userava  from jz_life_show jls left join  jz_users ju " +
            "              on jls .life_show_ownerid =ju.useropenid  where jls .life_show_see ='1' OR  " +
            "             (jls.life_show_see='0' and jls.life_show_ownerid=?1) ) abc ",nativeQuery = true)
    Page<Map<String,Object>> queryLifeShow(String openid, Pageable pageable);
}
