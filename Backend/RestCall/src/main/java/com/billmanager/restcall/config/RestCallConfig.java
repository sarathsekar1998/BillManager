package com.billmanager.restcall.config;

import com.billmanager.restcall.service.impl.JwtAuthentication;
import com.billmanager.restcall.service.impl.UserDetailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Log4j2
@ComponentScan(basePackages = "com.billmanager.restcall")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableWebSecurity
@EnableMongoRepositories(basePackages = "com.billmanager.restcall.repository")
public class RestCallConfig {

    @Autowired
    UserDetailService userDetailService;

    @Autowired
    JwtAuthentication jwtAuthentication;

    @Bean
    public CorsConfiguration corsConfiguration() {
        log.debug("--- start configuration cors ----");
        CorsConfiguration cors = new CorsConfiguration();
        cors.addAllowedOrigin("*");
        log.debug("allowed origin : "+cors.getAllowedOrigins());
        cors.setAllowedMethods(setAllowedMethods());
        log.debug("allowed methods : "+cors.getAllowedMethods());
        log.debug("---- end configuration cors ----");
        return cors;
    }

    @Bean(name = "manager")
    public AuthenticationManager manager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        log.debug("before dao authentication provider configuration");
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        daoAuthenticationProvider.setPasswordEncoder(encoder());
        log.debug("after dao authentication provider configuration completed :"+daoAuthenticationProvider);
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    public List<String> setAllowedMethods() {
        return List.of(
                HttpMethod.POST.name(),
                HttpMethod.GET.name(),
                HttpMethod.PUT.name(),
                HttpMethod.PATCH.name(),
                HttpMethod.DELETE.name()
        );

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests(reg->reg.requestMatchers("/auth/**").permitAll().anyRequest().authenticated());
        return http.build();
    }

    @Bean
    public FilterRegistrationBean registration() {
        log.debug("before jwt filter intialization");
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new JwtFilter(jwtAuthentication,invalidToken()));
        log.debug("after jwt filter completed");
        return filterRegistration;
    }

    @Bean
    public SimpleDateFormat dateFormat(){
        return new SimpleDateFormat("dd MMMM YYYY HH:mm:ss");
    }

    @Bean
    public List<String> invalidToken(){
        return new ArrayList<>();
    }




}
