package com.zhuhuix.startup.security.security;

import com.zhuhuix.startup.security.utils.JwtTokenUtils;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * token配置
 * @author zhuhuix
 * @date 2020-03-30
 */
public class JwtTokenConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtTokenUtils jwtTokenUtils;

    public JwtTokenConfigurer(JwtTokenUtils jwtTokenUtils){

        this.jwtTokenUtils = jwtTokenUtils;
    }

    @Override
    public void configure(HttpSecurity http) {
        JwtAuthenticationTokenFilter customFilter = new JwtAuthenticationTokenFilter(jwtTokenUtils);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
