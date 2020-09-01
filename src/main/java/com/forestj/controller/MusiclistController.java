package com.forestj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.forestj.pojo.*;
import com.forestj.service.MusiclistService;
import com.forestj.service.SongService;
import com.forestj.service.UserService;
import com.forestj.vo.AddMusicVo;
import com.forestj.vo.DeleteSongVo;
import com.forestj.vo.MusiclistVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("musiclist")
@CrossOrigin
public class MusiclistController {
    @Autowired
    private MusiclistService musiclistService;
    @Autowired
    private CurrentUser currentUser;
    @Autowired
    private UserService userService;
    @Autowired
    private SongService songService;

    /**
     * 查询歌单是否存在
     * @param name
     * @return
     */
    @GetMapping("name")
    public ResponseJson exists(@NotNull String name) {
        Integer userId = Integer.valueOf(currentUser.getCurrentUser().getUsername());
        Musiclist musiclist = musiclistService.exists(name, userId);
        if (musiclist != null) {
            return new ResponseJson(ResultCode.UNVALIDPARAMS);
        }
        return new ResponseJson(ResultCode.SUCCESS);
    }

    /**
     * 新建歌单
     * @param name
     * @return
     */
    @GetMapping("create")
    public ResponseJson<MusiclistVo> create(@NotNull String name) {
        Integer userId = Integer.valueOf(currentUser.getCurrentUser().getUsername());
        Musiclist musiclist = musiclistService.exists(name, userId);
        if (musiclist != null) {
            return new ResponseJson(ResultCode.UNVALIDPARAMS);
        }
        musiclist = new Musiclist();
        musiclist.setName(name);
        musiclist.setNum(0);
        musiclist.setOwnerId(userId);
        musiclist.setCreateTime(new Date());
        musiclistService.create(musiclist);
        System.out.println(musiclist.getId());
        System.out.println(userId);
        String username = userService.getUserInfo(userId).getUsername();
        MusiclistVo musiclistVo = new MusiclistVo();
        BeanUtils.copyProperties(musiclist, musiclistVo);
        musiclistVo.setOwner(username);
        return new ResponseJson<>(ResultCode.SUCCESS, musiclistVo);
    }

    /**
     * 查询歌单列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("query")
    public ResponseJson<PageResult<MusiclistVo>> queryList(@RequestParam(required = false, defaultValue = "1") int pageNum,
                                                           @RequestParam(required = false, defaultValue = "15") int pageSize) {
        if (pageNum <= 0 || pageSize <= 0 || pageSize > 100) {
            return new ResponseJson<>(ResultCode.UNVALIDPARAMS);
        }
        Integer userId = Integer.valueOf(currentUser.getCurrentUser().getUsername());
        String username = userService.getUserInfo(userId).getUsername();
        Page<Musiclist> musiclistPage = musiclistService.selectMusicByUserId(userId, pageNum, pageSize);
        PageResult ret = new PageResult<>(musiclistPage);
        List<MusiclistVo> lists = musiclistPage.getRecords().stream().map(musiclist -> {
            MusiclistVo musiclistVo = new MusiclistVo();
            BeanUtils.copyProperties(musiclist, musiclistVo);
            musiclistVo.setOwner(username);
            return musiclistVo;
        }).collect(Collectors.toList());
        ret.setRecords(lists);
        return new ResponseJson<>(ResultCode.SUCCESS, ret);
    }

    /**
     * 在歌单中新增歌曲
     * @param addMusicVo
     * @return
     */
    @PostMapping("add")
    public ResponseJson add(@RequestBody @Valid AddMusicVo addMusicVo) {
        Integer musicListId = addMusicVo.getMusicListId();
        Integer singId = addMusicVo.getSingId();
        Integer userId = Integer.valueOf(currentUser.getCurrentUser().getUsername());
        Song song = songService.findSongById(singId);
        Musiclist musiclist = musiclistService.selectMusicListById(musicListId, userId);
        if (song == null || musiclist == null || !musiclist.getOwnerId().equals(userId)) {
            return new ResponseJson(ResultCode.UNVALIDPARAMS);
        }
        int res = musiclistService.add(musicListId, singId);
        if (res != 0){
            musiclist.setNum(musiclist.getNum() + 1);
            musiclistService.update(musiclist);
        }
        return new ResponseJson(ResultCode.SUCCESS);
    }

    /**
     * 获取歌单中的歌曲列表
     * @param id
     * @return
     */
    @GetMapping("getList")
    public ResponseJson<List<Song>> getList(@NotNull Integer id){
        JwtUser user = this.currentUser.getCurrentUser();
        Integer userId = -1;
        if(user != null && user.getUsername() != null){
            userId = Integer.valueOf(user.getUsername());
        }
        Musiclist musiclist = musiclistService.selectMusicListById(id, userId);
        if(musiclist == null){
            return new ResponseJson<>(ResultCode.UNVALIDPARAMS);
        }
        List<Song> songs = songService.getSongByMusicList(id);
        return new ResponseJson<List<Song>>(ResultCode.SUCCESS, songs);
    }

    /**
     * 删除歌单
     * @param id
     * @return
     */
    @GetMapping("delete")
    public ResponseJson delete(@NotNull Integer id){
        Integer userId = Integer.valueOf(currentUser.getCurrentUser().getUsername());
        Musiclist musiclist = musiclistService.selectMusicListById(id, userId);
        if(musiclist == null || !musiclist.getOwnerId().equals(userId)){
            return new ResponseJson<>(ResultCode.UNVALIDPARAMS);
        }
        musiclistService.deleteMusicList(id);
        return new ResponseJson(ResultCode.SUCCESS);
    }

    /**
     *
     * 移除歌单中的歌曲
     * @param deleteSongVo
     * @return
     */
    @PostMapping("deleteSong")
    public ResponseJson deleteSong(@RequestBody @Valid DeleteSongVo deleteSongVo){
        Integer id = deleteSongVo.getId();
        Integer[] deleteId = deleteSongVo.getDeleteId();
        Integer userId = Integer.valueOf(currentUser.getCurrentUser().getUsername());
        Musiclist musiclist = musiclistService.selectMusicListById(id, userId);
        if(musiclist == null || !musiclist.getOwnerId().equals(userId)){
            return new ResponseJson<>(ResultCode.UNVALIDPARAMS);
        }
        int deleteSongs = musiclistService.deleteSongs(id, deleteId);
        musiclist.setNum(musiclist.getNum() - deleteSongs);
        musiclistService.update(musiclist);
        return new ResponseJson(ResultCode.SUCCESS);
    }

    /**
     * 获取歌单详情
     * @param id
     * @return
     */
    @GetMapping("get")
    public ResponseJson<MusiclistVo> getListById(@NotNull Integer id){
        Integer userId = Integer.valueOf(currentUser.getCurrentUser().getUsername());
        Musiclist musiclist = musiclistService.selectMusicListById(id, userId);
        UserInfo userInfo = userService.getUserInfo(musiclist.getOwnerId());
        MusiclistVo musiclistVo = new MusiclistVo();
        BeanUtils.copyProperties(musiclist, musiclistVo);
        musiclistVo.setOwner(userInfo.getUsername());
        return new ResponseJson<>(ResultCode.SUCCESS, musiclistVo);
    }

    /**
     * 更改歌单状态
     * @param open
     * @return
     */
    @GetMapping("changeState")
    public ResponseJson changeState(Integer id, boolean open){
        Integer userId = Integer.valueOf(currentUser.getCurrentUser().getUsername());
        Musiclist musiclist = musiclistService.selectMusicListById(id, userId);
        if(musiclist == null || !musiclist.getOwnerId().equals(userId)){
            return new ResponseJson<>(ResultCode.UNVALIDPARAMS);
        }
        musiclist.setOpen(open);
        musiclistService.update(musiclist);
        return new ResponseJson(ResultCode.SUCCESS);
    }


    /**
     * 搜索歌单
     * @param word
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("search")
    public ResponseJson<PageResult<Musiclist>> search(String word,
                                                 @RequestParam(required = false, defaultValue = "1") int pageNum,
                                                 @RequestParam(required = false, defaultValue = "10") int pageSize){
        Page<Musiclist> result = musiclistService.search(word, pageNum, pageSize);
        return new ResponseJson<>(ResultCode.SUCCESS, new PageResult<>(result));

    }
}
