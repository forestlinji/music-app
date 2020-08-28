package com.forestj.pojo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * 分页查询结果
 * @param <T>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    /**
     * 查询数据列表
     */
    private List<T> records = Collections.emptyList();

    /**
     * 总数
     */
    private long total = 0;

    /**
     * 每页显示条数，默认 15
     */
    private long size = 15;

    /**
     * 当前页
     */
    private long current = 1;

    public PageResult(IPage page) {
        this.setRecords(page.getRecords());
        this.setTotal(page.getTotal());
        this.setSize(page.getSize());
        this.setCurrent(page.getCurrent());
    }
}
