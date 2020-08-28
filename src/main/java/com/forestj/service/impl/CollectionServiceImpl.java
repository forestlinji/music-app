package com.forestj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.forestj.mapper.CollectionMapper;
import com.forestj.mapper.UserMapper;
import com.forestj.pojo.Collect;
import com.forestj.pojo.Song;
import com.forestj.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    private CollectionMapper collectionMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Song> getCollections(Integer userId) {
        return collectionMapper.getCollections(userId);
    }

    @Override
    public void addCollection(Integer userId, int id) {

        int addNum = collectionMapper.insert(new Collect(userId, id));
        userMapper.addCollection(addNum, userId);
    }

    @Override
    @Transactional
    public void deleteCollection(Integer userId, Integer[] ids) {
        int deleteNum = collectionMapper.delete(new UpdateWrapper<Collect>().eq("user_id", userId).
                in("music_id", Arrays.asList(ids)));
        userMapper.addCollection(-deleteNum, userId);
    }

    @Override
    public boolean exists(Integer userId, Integer id) {
        Collect collect = collectionMapper.selectOne(
                new QueryWrapper<Collect>()
                        .eq("user_id", userId)
                        .eq("music_id", id));
        return collect != null;
    }
}
