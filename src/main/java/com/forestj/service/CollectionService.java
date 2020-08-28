package com.forestj.service;

import com.forestj.pojo.Song;

import java.util.List;

public interface CollectionService {
    /**
     * 获取用户收藏
     * @param userId
     * @return
     */
    List<Song> getCollections(Integer userId);

    /**
     * 添加收藏
     * @param userId
     * @param id
     */
    void addCollection(Integer userId, int id);

    /**
     * 删除收藏
     * @param userId
     * @param ids
     */
    void deleteCollection(Integer userId, Integer[] ids);

    /**
     * 是否存在该收藏
     * @param userId
     * @param id
     * @return
     */
    boolean exists(Integer userId, Integer id);
}
