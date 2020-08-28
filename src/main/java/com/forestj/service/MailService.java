package com.forestj.service;

/**
 * 邮件服务
 */
public interface MailService {
    /**
     * 发送激活邮件
     * @param email
     * @param token
     */
    public void SendActiveMail(String email,String token);

    /**
     * 发送重置密码邮件
     * @param email
     * @param changeToken
     */
    public void SendForgetMail(String email,String changeToken);
}
