package com.lipcoding.restfulwebservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
        throws Exception {
        // 사용할 수 있는 아이디를 코드상에서 설정한다.
        auth.inMemoryAuthentication()
            .withUser("kenneth")
            .password("{noop}test1234")
            .roles("USER");
    }
}
