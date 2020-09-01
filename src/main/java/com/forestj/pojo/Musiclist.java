package com.forestj.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Musiclist {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer ownerId;
    private String name;
    private Integer num;
    private Date createTime;
    @TableField(exist = false)
    @JsonIgnore
    private List<Song> musics;
    @JsonIgnore
    @TableLogic
    private boolean deleted;
    private boolean open;
}
