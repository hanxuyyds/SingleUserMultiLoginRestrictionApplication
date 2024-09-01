package com.example.singleusermultiloginrestriction.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    // 账号名称，作为唯一标识
    private String username;
    // 用户姓名
    private String name;
    private String sessionId;


}
