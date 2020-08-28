package com.forestj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.forestj.pojo.*;
import com.forestj.service.CollectionService;
import com.forestj.service.CommentService;
import com.forestj.service.SongService;
import com.forestj.service.UserService;
import com.forestj.vo.AddCommentVo;
import com.forestj.vo.CommentVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@RestController
@RequestMapping("comment")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private SongService songService;
    @Autowired
    private CurrentUser currentUser;
    @Autowired
    private UserService userService;

    /**
     * 发表评论
     * @param addCommentVo
     * @return
     */
    @PostMapping("add")
    public ResponseJson<CommentVo> add(@RequestBody @Valid AddCommentVo addCommentVo) {
        String content = addCommentVo.getComment();
        Integer songId = addCommentVo.getSongId();
        Integer userId = Integer.valueOf(currentUser.getCurrentUser().getUsername());
        String username = userService.getUserInfo(userId).getUsername();
        Song song = songService.findSongById(songId);
        if (song == null) {
            return new ResponseJson<>(ResultCode.UNVALIDPARAMS);
        }
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setReviewerId(userId);
        comment.setSongId(songId);
        comment.setCreateTime(new Date());
        commentService.insert(comment);
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment, commentVo);
        commentVo.setReviewerName(username);
        return new ResponseJson<>(ResultCode.SUCCESS, commentVo);
    }

    /**
     * 查询某首歌的评论
     * @param id
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("query")
    public ResponseJson<PageResult<CommentVo>> query(Integer id,
                                                     @RequestParam(required = false, defaultValue = "1") int pageNum,
                                                     @RequestParam(required = false, defaultValue = "15") int pageSize) {
        if (pageNum <= 0 || pageSize <= 0 || pageSize > 100) {
            return new ResponseJson<>(ResultCode.UNVALIDPARAMS);
        }
        Page<CommentVo> commentVoPage = commentService.query(id, pageNum, pageSize);
        return new ResponseJson<>(ResultCode.SUCCESS, new PageResult<>(commentVoPage));
    }

    /**
     * 查看自己的评论
     * @param id
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("queryMy")
    public ResponseJson<PageResult<CommentVo>> queryMy(Integer id,
                                                       @RequestParam(required = false, defaultValue = "1") int pageNum,
                                                       @RequestParam(required = false, defaultValue = "15") int pageSize) {
        if (pageNum <= 0 || pageSize <= 0 || pageSize > 100) {
            return new ResponseJson<>(ResultCode.UNVALIDPARAMS);
        }
        Integer userId = Integer.valueOf(currentUser.getCurrentUser().getUsername());

        Page<CommentVo> commentVoPage = commentService.queryMy(id, userId, pageNum, pageSize);
        return new ResponseJson<>(ResultCode.SUCCESS, new PageResult<>(commentVoPage));
    }

    /**
     * 删除评论
     * @param id
     * @return
     */
    @GetMapping("delete")
    public ResponseJson delete(@NotNull Integer id){
        Integer userId = Integer.valueOf(currentUser.getCurrentUser().getUsername());
        int delete = commentService.delete(id, userId);
        if(delete == 0){
            return new ResponseJson(ResultCode.UNVALIDPARAMS);
        }
        return new ResponseJson(ResultCode.SUCCESS);
    }
}
