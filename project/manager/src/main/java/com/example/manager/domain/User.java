package com.example.manager.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="User")
public class User {
    @Id
    String id;

    String userId;
    String userPassword;

    String name;
    
    String[] concern;
}
