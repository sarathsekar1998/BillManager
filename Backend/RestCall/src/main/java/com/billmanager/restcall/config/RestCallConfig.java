package com.billmanager.restcall.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

@Configuration
@ComponentScan(basePackages = "com.billmanager.restcall")
@EnableMongoRepositories(basePackages = "com.billmanager.restcall.repository")
public class RestCallConfig {
}
