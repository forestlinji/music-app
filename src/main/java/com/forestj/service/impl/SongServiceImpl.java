package com.forestj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.forestj.mapper.SongMapper;
import com.forestj.pojo.Song;
import com.forestj.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongServiceImpl implements SongService {
    @Autowired
    private SongMapper songMapper;

    @Override
    public Page<Song> search(String word, int pageNum, int pageSize) {
        return songMapper.selectPage(new Page<Song>(pageNum, pageSize),
                                    new QueryWrapper<Song>().like("name", word)
                                                            .or()
                                                            .like("singer", word));
    }

    @Override
    public List<Song> getRandomSongs(int num) {
        return songMapper.getRandomSongs(num);
    }

    @Override
    public Song findSongById(Integer songId) {
        return songMapper.selectById(songId);
    }

    @Override
    public List<Song> getSongByMusicList(Integer id) {
        return songMapper.getSongByMusicList(id);
    }

    @Override
    public void add(Song song) {
        songMapper.insert(song);
    }

    @Override
    public void update(Song song) {
        songMapper.updateById(song);
    }

    @Override
    public Page<Song> getAll(int pageNum, int pageSize) {
        return songMapper.selectPage(new Page<Song>(pageNum, pageSize),null);
    }

    @Override
    public int delete(Integer songId) {
        return songMapper.deleteById(songId);
    }
}
