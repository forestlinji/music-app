package com.forestj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.forestj.pojo.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
    /**
     * 获取收藏数量
     * @param id
     * @return
     */
    @Select("select collects from user where user_id = #{id}")
    int getCollectsNum(Integer id);

    /**
     * 添加收藏
     * @param num
     * @param id
     */
    @Update("update user set collects = collects + #{num} where user_id = #{id}")
    void addCollection(int num, Integer id);
}
