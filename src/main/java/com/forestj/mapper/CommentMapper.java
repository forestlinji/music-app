package com.forestj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.forestj.pojo.Comment;
import com.forestj.vo.CommentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CommentMapper extends BaseMapper<Comment> {
    /**
     * 查询评论
     * @param commentVoPage
     * @param songId
     * @return
     */
    @Select("SELECT c.id, c.song_id, c.reviewer_id, c.content, c.create_time, u.username as reviewerName \n" +
            "FROM `comment` c, `user` u where c.song_id = #{songId} AND u.user_id = c.reviewer_id")
    Page<CommentVo> queryComment(Page<CommentVo> commentVoPage,Integer songId);

    /**
     * 查询我的评论
     * @param commentVoPage
     * @param songId
     * @param userId
     * @return
     */
    @Select("SELECT c.id, c.song_id, c.reviewer_id, c.content, c.create_time, u.username as reviewerName \n" +
            "FROM `comment` c, `user` u where c.song_id = #{songId} AND c.reviewer_id = #{userId} AND u.user_id = c.reviewer_id")
    Page<CommentVo> queryMyComment(Page<CommentVo> commentVoPage,Integer songId, Integer userId);
}
