package com.excel.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by mqjia on 5/31/2018.
 */
@Getter
@Setter
public class User {
    private Integer id;
    private String nickName;
    private String loginName;
    private String password;
}
