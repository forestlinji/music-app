package com.forestj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forestj.pojo.Collect;
import com.forestj.pojo.Song;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CollectionMapper extends BaseMapper<Collect> {
    /**
     * 查询收藏
     * @param id
     * @return
     */
    @Select("SELECT s.* from song s,collect c WHERE c.user_id = #{id} && c.music_id = s.id")
    List<Song> getCollections(Integer id);
}
