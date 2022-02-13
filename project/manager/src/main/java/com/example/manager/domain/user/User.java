package com.example.manager.domain.user;

import com.azure.data.tables.models.TableEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    String userId;
    String userPassword;

    String emailAddr;
    String name;


    //    String[] concern;
//
//    String[] like;
//    String[] hate;
}
