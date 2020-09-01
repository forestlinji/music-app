package com.forestj.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MusiclistVo {
    private Integer id;
    private Integer ownerId;
    private String owner;
    private String name;
    private Integer num;
    private Date createTime;
    private boolean open;

}
