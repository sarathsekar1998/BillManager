package com.billmanager.restcall.dao;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("USER_TABLE")
@Data
@Log4j2
public class User {

    @Id
    private String id;
    private String name;
    private Long mobile;
    private String password;
    private String fingerId;
    private String device;
    private String refreshToken;
    private String accesstoken;
    private double needTOPay;
    private double paid;
    private LocalDateTime updatedOn;
    private LocalDateTime CreatedOn;

}
