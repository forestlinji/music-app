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
public class RegisterVo {
    @NotNull
    private String username;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Length(min = 6)
    private String password;

    @NotNull
    private String activeCode;
}
