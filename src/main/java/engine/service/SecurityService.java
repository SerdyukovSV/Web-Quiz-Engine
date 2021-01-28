package engine.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityService extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        log.debug("Started configure http security");
        httpSecurity
                .csrf().disable().httpBasic()
                .and()
                .formLogin().disable();
        httpSecurity
                .headers().frameOptions().disable();
        httpSecurity.authorizeRequests()
                .antMatchers("/api/register").permitAll()
                .antMatchers("/actuator/shutdown").permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated();
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        log.debug("Started configure web security");
        webSecurity
                .ignoring()
                .antMatchers("/h2-console/**");
    }

    @Override
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.debug("Started configure authentication");
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}
