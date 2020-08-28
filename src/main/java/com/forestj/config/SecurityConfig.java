package com.forestj.config;


import com.forestj.exception.JWTAccessDeniedHandler;
import com.forestj.exception.JWTAuthenticationEntryPoint;
import com.forestj.filter.JWTAuthenticationFilter;
import com.forestj.filter.JWTAuthorizationFilter;
import com.forestj.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsUtils;



@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    /**
     * 密码编码器
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService createUserDetailsService() {
        return userDetailsServiceImpl;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 设置自定义的userDetailsService以及密码编码器
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                // 禁用 CSRF
                .csrf().disable()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers(HttpMethod.POST, "/user/login").permitAll()
                .antMatchers("/user/change").authenticated()
                .antMatchers("/user/collects").authenticated()
                .antMatchers("/user/getUserInfo").authenticated()
                .antMatchers("/user/getRole").authenticated()
                .antMatchers("/song/collect").authenticated()
                .antMatchers("/song/deleteCollect").authenticated()
                .antMatchers("/song/exists").authenticated()
                .antMatchers("/song/add").authenticated()
                .antMatchers("/song/delete").authenticated()
                .antMatchers("/musiclist/name").authenticated()
                .antMatchers("/musiclist/create").authenticated()
                .antMatchers("/musiclist/query").authenticated()
                .antMatchers("/musiclist/add").authenticated()
                .antMatchers("/musiclist/getList").authenticated()
                .antMatchers("/musiclist/delete").authenticated()
                .antMatchers("/musiclist/deleteSong").authenticated()
                .antMatchers("/comment/add").authenticated()
                .antMatchers("/comment/queryMy").authenticated()
                .antMatchers("/comment/delete").authenticated()

//                .antMatchers("/auth/changePassword").authenticated()
//                .antMatchers("/auth/username").authenticated()
//                .antMatchers("/auth/email").authenticated()
//                .antMatchers("/user/info").authenticated()
//                .antMatchers(HttpMethod.POST,"/user/avatar").authenticated()
//                .antMatchers("/user/introduction").authenticated()
//                .antMatchers("/user/reject").authenticated()
//                .antMatchers("/user/report").authenticated()
//                .antMatchers("/user/reportList").authenticated()
//                .antMatchers("/user/dealReport").authenticated()
//                .antMatchers("/user/banList").authenticated()
//                .antMatchers("/user/cancelBan").authenticated()
//                .antMatchers("/user/getUserBanList").authenticated()
//                .antMatchers("/user/ban").authenticated()
//                .antMatchers("/user/showAllUsers").authenticated()
//                .antMatchers("/user/admin").authenticated()
//                .antMatchers("/user/getAdminList").authenticated()
//                .antMatchers("/user/getUserAdminInfo").authenticated()
//                .antMatchers("/question/changeState").authenticated()
//                .antMatchers("/question/addQuestion").authenticated()
//                .antMatchers("/question/reply").authenticated()
//                .antMatchers("/question/getDeleted").authenticated()
//                .antMatchers(HttpMethod.DELETE,"/question").authenticated()
//                .antMatchers("/question/resume").authenticated()
//                .antMatchers("/question/getMyQuestioned").authenticated()
//                .antMatchers("/question/regret").authenticated()
//                .antMatchers("/question/getMyQuestion").authenticated()
//                .antMatchers("/message/getAll").authenticated()
                // 其他放行
                .anyRequest().permitAll()
                .and()
                //添加自定义Filter
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // 不需要session（不创建会话）
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 授权异常处理
                .exceptionHandling().authenticationEntryPoint(new JWTAuthenticationEntryPoint())
                .accessDeniedHandler(new JWTAccessDeniedHandler());
        // 防止H2 web 页面的Frame 被拦截
        http.headers().frameOptions().disable();
    }

}
