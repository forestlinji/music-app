package com.forestj.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.forestj.pojo.Musiclist;

public interface MusiclistService {
    /**
     * 查询是否存在歌单
     * @param name
     * @param userId
     * @return
     */
    Musiclist exists(String name, Integer userId);

    /**
     * 创建歌单
     * @param musiclist
     */
    void create(Musiclist musiclist);

    Page<Musiclist> selectMusicByUserId(Integer userId,int pageNum,int pageSize);

    Musiclist selectMusicListById(Integer listId, Integer userId);

    /**
     * 添加歌曲到歌单
     * @param musicListId
     * @param singId
     * @return
     */
    int add(Integer musicListId, Integer singId);

    /**
     * 更新歌单信息
     * @param musiclist
     */
    void update(Musiclist musiclist);

    /**
     * 删除歌单
     * @param id
     */
    void deleteMusicList(Integer id);

    int deleteSongs(Integer id,Integer[] deleteId);

    Page<Musiclist> search(String word, int pageNum, int pageSize);
}
