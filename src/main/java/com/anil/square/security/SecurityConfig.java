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
                .antMatchers("/", "/sendtestmail", "/addusers", "/test",  "/keys", "/captcha", "/login","/autoauth/{email}/{password}", "/**/*.css", "/JS/**",  "/customol/**", "/redis/**").permitAll()
                .antMatchers("/sendtestmail", "/dash").authenticated()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/error/403")
                .and()
                //  .csrf().disable()
            .logout()
            .logoutUrl("/logout")
             .logoutSuccessUrl("/")
                .permitAll()
              /*  .and()
                .headers()
            .xssProtection().disable()
            .contentSecurityPolicy("default-src 'self'; script-src 'self' 'unsafe-inline'; style-src 'self' 'unsafe-inline' https://fonts.googleapis.com https://cdnjs.cloudflare.com; img-src 'self' data:; font-src 'self' https://fonts.gstatic.com data:; connect-src 'self';")
            .and()
        .contentTypeOptions()
            .and()
        .cacheControl()
            .and()
        .frameOptions().disable()
            .and()  
        .csrf().disable()
        .sessionManagement()
            .maximumSessions(1)
            .expiredUrl("/login?invalid-session=true")
            .maxSessionsPreventsLogin(false) */
            ;
    }
          
    
    

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
    
}
