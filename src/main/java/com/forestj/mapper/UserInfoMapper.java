package com.forestj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forestj.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import javax.management.MXBean;

@Mapper
@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo> {
}
