package com.billmanager.restcall.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Configuration
@Log4j2
@ComponentScan(basePackages = "com.billmanager.restcall")
@EnableAspectJAutoProxy
@EnableMongoRepositories(basePackages = "com.billmanager.restcall.repository")
public class RestCallConfig {
    @Bean
    public CorsConfiguration corsConfiguration(){
       log.debug("--- start configuration cors ----");
       CorsConfiguration cors =  new CorsConfiguration();
       cors.setAllowedOrigins(Arrays.asList("*"));
       cors.setAllowedMethods(Arrays.asList("POST","GET","DELETE","PATCH","PUT","HEAD","OPTIONS"));
       log.debug("---- end configuration cors ----");
       return cors;
    }


}
