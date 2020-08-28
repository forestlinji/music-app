package com.forestj.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteSongVo {
    @NotNull
    private Integer id;
    @NotNull
    private Integer[] deleteId;
}
