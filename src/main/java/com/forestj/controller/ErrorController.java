package com.forestj.controller;

import com.forestj.pojo.ResponseJson;
import com.forestj.pojo.ResultCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;


/**
 * 统一异常处理
 */
@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseJson methodArgumentNotValid(){
        return new ResponseJson(ResultCode.UNVALIDPARAMS);
    }

    @ExceptionHandler(value = {ExpiredJwtException.class, SignatureException.class,MalformedJwtException.class,IllegalArgumentException.class})
    public ResponseJson wrongJwt(){
        return new ResponseJson(ResultCode.UNLOGIN);
    }

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public ResponseJson FileError(MaxUploadSizeExceededException e){
        return new ResponseJson(ResultCode.BIGFILE);
    }
}
