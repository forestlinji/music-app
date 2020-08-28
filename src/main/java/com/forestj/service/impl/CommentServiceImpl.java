package com.forestj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.forestj.mapper.CommentMapper;
import com.forestj.pojo.Comment;
import com.forestj.service.CommentService;
import com.forestj.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;


    @Override
    public void insert(Comment comment) {
        commentMapper.insert(comment);
    }

    @Override
    public Page<CommentVo> query(Integer songId, int pageNum, int pageSize) {
        return commentMapper.queryComment(new Page<CommentVo>(pageNum,pageSize), songId);
    }

    @Override
    public Page<CommentVo> queryMy(Integer songId, Integer userId, int pageNum, int pageSize) {
        return commentMapper.queryMyComment(new Page<CommentVo>(pageNum,pageSize), songId, userId);
    }

    @Override
    public int delete(Integer id, Integer userId) {
        return commentMapper.delete(
                new UpdateWrapper<Comment>()
                        .eq("id", id)
                        .eq("reviewer_id", userId));
    }


}
