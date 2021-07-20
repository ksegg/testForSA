package com.example.security;

import com.example.handle.MyAuthenctiationFailureHandler;
import com.example.handle.MyAuthenctiationSuccessHandler;
import com.example.service.LoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * 【名称】</br>
 * SpringSecurity設定
 *
 * @author eptsz01
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyAuthenctiationFailureHandler myAuthenctiationFailureHandler;
    @Autowired
    private MyAuthenctiationSuccessHandler myAuthenctiationSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 権限コントロール
        // ユーザ管理
//        http.authorizeRequests().antMatchers("/account-list", "/account-detail", "/regist-account")
//                .hasAnyRole(Constants.ROLE_SYSTEM);
        http.authorizeRequests()
                .antMatchers("/css/**", "/luckysheet/**", "/css/plugin/**", "/img/**", "/js/plugin/**","/js/**", "/webjars/**", "/top/*")
                .permitAll().anyRequest().authenticated().and().formLogin().loginPage("/login")
                .failureHandler(myAuthenctiationFailureHandler).successHandler(myAuthenctiationSuccessHandler)
                .permitAll().usernameParameter("login").passwordParameter("inputPassword").and().logout().permitAll();
//        http.formLogin().successForwardUrl("/home");
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/login").permitAll();
        http.csrf().disable();
    }

    @Bean
    UserDetailsService userDetailService() {
        return new LoginUserService();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService()).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
