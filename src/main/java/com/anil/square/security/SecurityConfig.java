package com.anil.square.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter  {

    @Override
    public void configure(HttpSecurity  http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/","/addusers", "/test", "/dash", "/keys",  "/login","/autoauth/{email}/{password}", "/**/*.css", "/JS/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .logout()
            .logoutUrl("/logout")
             .logoutSuccessUrl("/")
                .permitAll()
                ;   
    }
    

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
    
}
