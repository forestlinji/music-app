package com.forestj.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordVo {
    @NotNull
    @Length(min = 6)
    private String oldPassword;
    @NotNull
    @Length(min = 6)
    private String newPassword;
}
