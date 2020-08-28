package com.forestj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.forestj.pojo.Song;

import java.util.List;

public interface SongService {
    /**
     * 搜索音乐
     * @param word
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<Song> search(String word, int pageNum, int pageSize);

    /**
     * 随机展示音乐
     * @param num
     * @return
     */
    List<Song> getRandomSongs(int num);

    Song findSongById(Integer songId);

    List<Song> getSongByMusicList(Integer id);

    /**
     * 新增歌曲
     * @param song
     */
    void add(Song song);

    /**
     * 更新歌曲信息
     * @param song
     */
    void update(Song song);

    /**
     * 查询所有歌曲
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<Song> getAll(int pageNum,int pageSize);

    /**
     * 删除歌曲
     * @param songId
     * @return
     */
    int delete(Integer songId);

}
