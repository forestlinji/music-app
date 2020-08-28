package com.forestj.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForgetPasswordVo {
    @NotNull
    @Email
    private String email;
    @NotNull
    private String changeToken;
    @NotNull
    @Length(min = 6)
    private String newPassword;
}
