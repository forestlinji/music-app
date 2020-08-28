package com.forestj;

import com.forestj.pojo.Musiclist;
import com.forestj.vo.MusiclistVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class MusicApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void test01(){
        Musiclist musiclist = new Musiclist(1, 2, "dasd", 3, new Date(), null, false);
        MusiclistVo musiclistVo = new MusiclistVo();
        BeanUtils.copyProperties(musiclist, musiclistVo);
        System.out.println(musiclistVo);

    }
}
