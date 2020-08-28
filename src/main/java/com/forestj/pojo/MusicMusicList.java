package com.forestj.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "music_musiclist")
public class MusicMusicList {
    private Integer id;
    private Integer musicId;

}
