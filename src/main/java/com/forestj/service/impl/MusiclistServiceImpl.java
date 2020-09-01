package com.forestj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.forestj.mapper.MusicMusicListMapper;
import com.forestj.mapper.MusiclistMapper;
import com.forestj.pojo.MusicMusicList;
import com.forestj.pojo.Musiclist;
import com.forestj.service.MusiclistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MusiclistServiceImpl implements MusiclistService {

    @Autowired
    private MusiclistMapper musiclistMapper;
    @Autowired
    private MusicMusicListMapper musicMusicListMapper;

    @Override
    public Musiclist exists(String name, Integer userId) {
        QueryWrapper<Musiclist> wrapper = new QueryWrapper<Musiclist>()
                .eq("owner_id", userId)
                .eq("name", name);
//                .eq("deleted", 0)
        return musiclistMapper.selectOne(wrapper);
    }

    @Override
    public void create(Musiclist musiclist) {
        musiclistMapper.insert(musiclist);
    }

    @Override
    public Page<Musiclist> selectMusicByUserId(Integer userId, int pageNum, int pageSize) {
        return musiclistMapper.selectPage(
                new Page<Musiclist>(pageNum, pageSize),
                new QueryWrapper<Musiclist>().eq("owner_id", userId));
    }

    @Override
    public Musiclist selectMusicListById(Integer listId, Integer userId) {
        return musiclistMapper.selectOne(
                new QueryWrapper<Musiclist>()
                        .eq("id", listId)
                        .and(wrapper -> wrapper.eq("owner_id", userId).or().eq("open", 1))

        );
    }

    @Override
    public int add(Integer musicListId, Integer singId) {
        MusicMusicList musicMusicList = musicMusicListMapper.selectOne(new QueryWrapper<MusicMusicList>()
                .eq("musiclist_id", musicListId)
                .eq("music_id", singId));
        if(musicMusicList != null){
            return 0;
        }
        return musiclistMapper.add(musicListId,singId);
    }

    @Override
    public void update(Musiclist musiclist) {
        musiclistMapper.updateById(musiclist);
    }

    @Override
    public void deleteMusicList(Integer id) {
        musiclistMapper.deleteById(id);
    }

    @Override
    public int deleteSongs(Integer id, Integer[] deleteId) {
        return musicMusicListMapper.delete(
                new UpdateWrapper<MusicMusicList>()
                .eq("musiclist_id", id)
                .in("music_id", deleteId)
        );
    }

    @Override
    public Page<Musiclist> search(String word, int pageNum, int pageSize) {
        return musiclistMapper.selectPage(
                new Page<Musiclist>(pageNum, pageSize),
                new QueryWrapper<Musiclist>()
                        .like("name", word)
                        .eq("open", 1));
    }
}
