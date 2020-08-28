package com.forestj.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVo {
    private Integer id;
    private Integer songId;
    private Integer reviewerId;
    public String reviewerName;
    private String content;
    private Date createTime;
}
