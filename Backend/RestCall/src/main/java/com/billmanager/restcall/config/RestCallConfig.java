package com.billmanager.restcall.config;

import com.billmanager.restcall.service.impl.UserDetailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import java.util.List;

@Configuration
@Log4j2
@ComponentScan(basePackages = "com.billmanager.restcall")
@EnableAspectJAutoProxy
@EnableMongoRepositories(basePackages = "com.billmanager.restcall.repository")
public class RestCallConfig {

    @Autowired
    UserDetailService userDetailService;



    @Bean
    public CorsConfiguration corsConfiguration() {
        log.debug("--- start configuration cors ----");
        CorsConfiguration cors = new CorsConfiguration();
        cors.addAllowedOrigin("*");
        cors.setAllowedMethods(setAllowedMethods());
        log.debug("---- end configuration cors ----");
        return cors;
    }

    @Bean
    public AuthenticationManager manager(AuthenticationManagerBuilder builder) throws Exception {
        return builder.authenticationProvider(authenticationProvider()).build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        daoAuthenticationProvider.setPasswordEncoder(encoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    public List<String> setAllowedMethods(){
       return List.of(
                HttpMethod.POST.name(),
                HttpMethod.GET.name(),
                HttpMethod.PUT.name(),
                HttpMethod.PATCH.name(),
                HttpMethod.DELETE.name()
        );
    }



}
