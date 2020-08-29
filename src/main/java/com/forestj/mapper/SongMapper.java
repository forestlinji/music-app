package com.forestj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forestj.pojo.Song;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Repository
public interface SongMapper extends BaseMapper<Song> {

    @Select("select id,name,singer,longs,link,image from song order by rand() limit #{num}")
    List<Song> getRandomSongs(int num);

    @Select("SELECT s.id,s.`name`,s.singer,s.longs,s.link,s.image \n" +
            "FROM song s,music_musiclist m\n" +
            "WHERE s.id = m.music_id AND m.musiclist_id = #{id}")
    List<Song> getSongByMusicList(Integer id);

}
