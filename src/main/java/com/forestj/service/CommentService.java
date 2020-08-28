package com.forestj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.forestj.pojo.Comment;
import com.forestj.vo.CommentVo;

public interface CommentService {
    /**
     * 增加评论
     * @param comment
     */
    void insert(Comment comment);

    /**
     * 查询评论
     * @param songId
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<CommentVo> query(Integer songId, int pageNum, int pageSize);

    /**
     * 查询我的评论
     * @param songId
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<CommentVo> queryMy(Integer songId, Integer userId, int pageNum, int pageSize);

    /**
     * 删除评论
     * @param id
     * @param userId
     * @return
     */
    int delete(Integer id, Integer userId);
}
