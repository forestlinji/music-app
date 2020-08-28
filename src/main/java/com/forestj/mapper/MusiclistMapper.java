package com.forestj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forestj.pojo.Musiclist;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MusiclistMapper extends BaseMapper<Musiclist> {

    @Insert("insert into music_musiclist values (#{MusiclistId}, #{songId})")
    int add(Integer MusiclistId, Integer songId);
}
