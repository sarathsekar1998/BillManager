package com.billmanager.restcall.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("EXPENCE_TABLE")
public class ExpenceDetails {
    @Id
    private String id;
    private String item_description;
    private String groupId;
    private List<ExpencePuchase> usersPuchases;
    private String type;
}
