package com.forestj.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Song {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String singer;
    private Integer longs;
    private String link;
    private String image;
    @JsonIgnore
    @TableLogic
    private boolean deleted;
}
