package com.forestj.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.CannedAccessControlList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.forestj.mapper.SongMapper;
import com.forestj.pojo.*;
import com.forestj.service.CollectionService;
import com.forestj.service.SongService;
import com.forestj.service.UserService;
import com.forestj.utils.FileUtil;
import com.forestj.vo.DeleteCollectVo;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("song")
@CrossOrigin
public class SongController {
    @Autowired
    private SongService songService;
    @Autowired
    private CurrentUser currentUser;
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private UserService userService;
    @Autowired
    OSS ossClient;

    /**
     * 搜索歌曲
     * @param word
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("search")
    public ResponseJson<PageResult<Song>> search(String word,
                                                 @RequestParam(required = false, defaultValue = "1") int pageNum,
                                                 @RequestParam(required = false, defaultValue = "15") int pageSize) {
        if (pageNum <= 0 || pageSize <= 0 || pageSize > 100) {
            return new ResponseJson<>(ResultCode.UNVALIDPARAMS);
        }
        Page<Song> result = songService.search(word, pageNum, pageSize);
        return new ResponseJson<>(ResultCode.SUCCESS, new PageResult<>(result));
    }

    /**
     * 随机展示歌曲
     * @param num
     * @return
     */
    @GetMapping("random")
    public ResponseJson<List<Song>> getRandomSongs(@RequestParam(required = false, defaultValue = "4") int num) {
        List<Song> randomSongs = songService.getRandomSongs(num);
        return new ResponseJson<>(ResultCode.SUCCESS, randomSongs);
    }

    /**
     * 将歌曲加入收藏
     * @param id
     * @return
     */
    @GetMapping("collect")
    public ResponseJson addCollect(@NotNull int id){
        Integer userId = Integer.valueOf(currentUser.getCurrentUser().getUsername());
        int collectsNum = userService.getCollectsNum(userId);
        if(collectsNum > 150){
            return new ResponseJson(ResultCode.UNVALIDPARAMS);
        }
        Song song = songService.findSongById(id);
        if (song == null) {
            return new ResponseJson(ResultCode.UNVALIDPARAMS);
        }
        collectionService.addCollection(userId, id);
        return new ResponseJson(ResultCode.SUCCESS);
    }

    /**
     * 删除收藏
     * @param deleteCollectVo
     * @return
     */
    @PostMapping("deleteCollect")
    public ResponseJson deleteCollect(@RequestBody DeleteCollectVo deleteCollectVo){
        Integer[] deleteId = deleteCollectVo.getDeleteId();
//        System.out.println(deleteId.length);
//        System.out.println(deleteId[0]);
        Integer userId = Integer.valueOf(currentUser.getCurrentUser().getUsername());
        collectionService.deleteCollection(userId, deleteId);
        return new ResponseJson(ResultCode.SUCCESS);
    }

    /**
     * 查询歌曲是否已收藏
     * @param id
     * @return
     */
    @GetMapping("exists")
    public ResponseJson exists(@NotNull Integer id){
        Integer userId = Integer.valueOf(currentUser.getCurrentUser().getUsername());
        boolean exists = collectionService.exists(userId, id);
        return new ResponseJson(exists ? ResultCode.EXISTS : ResultCode.SUCCESS);
    }

    /**
     * 上传歌曲
     * @param name
     * @param singer
     * @param music
     * @param image
     * @return
     */
    @PostMapping("add")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ROOT')")
    public ResponseJson add(String name,
                            String singer,
                            MultipartFile music,
                            @RequestParam(value = "image", required = false) MultipartFile image){
        if(music == null){
            return new ResponseJson(ResultCode.UNVALIDPARAMS);
        }
//        CommonsMultipartFile cf = (CommonsMultipartFile) music;//此处file 是你的MultipartFile
//        DiskFileItem fi = (DiskFileItem) cf.getFileItem();
//        File musics = fi.getStoreLocation();//会在项目的根目录的临时文件夹下生成一个临时文件 *.tmp
        File musics = null;
        try {
            musics = FileUtil.MultipartFileToFile(music);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseJson(ResultCode.UNVALIDPARAMS);
        }
        int length = 0;
        try {
            MP3File f = (MP3File) AudioFileIO.read(musics);
            MP3AudioHeader audioHeader = (MP3AudioHeader) f.getAudioHeader();
            // System.out.println(audioHeader.getTrackLength());
            length = audioHeader.getTrackLength();
            if(length <= 0){
                throw new Exception("not a song");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseJson(ResultCode.UNVALIDPARAMS);
        }
        Song song = new Song();
        song.setName(name);
        song.setSinger(singer);
        song.setLongs(length);
        songService.add(song);
        String link = getLink(song);
        ossClient.putObject("forestj", link, musics);
        song.setLink("/" + link);
        if(isImage(image)){
//            CommonsMultipartFile imagee = (CommonsMultipartFile) image;//此处file 是你的MultipartFile
//            DiskFileItem dfi = (DiskFileItem) imagee.getFileItem();
//            File images = dfi.getStoreLocation();//会在项目的根目录的临时文件夹下生成一个临时文件 *.tmp
            File images = null;
            try {
                images = FileUtil.MultipartFileToFile(image);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseJson(ResultCode.UNVALIDPARAMS);
            }
            String imageLink = getImage(song);
            ossClient.putObject("forestj", imageLink, images);
            song.setImage("/" + imageLink);
        }
        songService.update(song);
        return new ResponseJson(ResultCode.SUCCESS);
    }

    /**
     * 查询所有歌曲
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("getAll")
    public ResponseJson<PageResult<Song>> getAll(
            @RequestParam(required = false, defaultValue = "1") int pageNum,
            @RequestParam(required = false, defaultValue = "15") int pageSize){
        if (pageNum <= 0 || pageSize <= 0 || pageSize > 100) {
            return new ResponseJson<>(ResultCode.UNVALIDPARAMS);
        }
        Page<Song> result = songService.getAll(pageNum, pageSize);
        return new ResponseJson<>(ResultCode.SUCCESS, new PageResult<>(result));
    }

    /**
     * 删除歌曲
     * @param id
     * @return
     */
    @GetMapping("delete")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ROOT')")
    public ResponseJson delete(Integer id){
        Song song = songService.findSongById(id);
        if(song != null){
            int num = songService.delete(id);
            if(num != 0){
                ossClient.setObjectAcl("forestj", getLink(song), CannedAccessControlList.Private);
            }
        }else {
            return new ResponseJson(ResultCode.UNVALIDPARAMS);
        }
        return new ResponseJson(ResultCode.SUCCESS);
    }


    private String getLink(Song song){
        String link = "music/music/" + song.getId() + "_" + song.getName() + ".mp3";
        return link;
    }


    private String getImage(Song song){
        String image = "music/img/" + song.getId() + "_" + song.getName() + ".jpg";
        return image;
    }


    private boolean isImage(MultipartFile image){
        if(image == null){
            return false;
        }
        //检查是否是图片
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(image.getInputStream());
            if(bi == null){
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
