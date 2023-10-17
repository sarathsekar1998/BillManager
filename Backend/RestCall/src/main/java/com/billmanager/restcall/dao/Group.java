package com.billmanager.restcall.dao;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("GROUP_TABLE")
@Data
@Log4j2
public class Group {
    @Id
    private String id;
    private String adminId;
    private String groupName;
    private List<String> usersId;
    private LocalDateTime updateOn;
    private LocalDateTime createdOn;
}
