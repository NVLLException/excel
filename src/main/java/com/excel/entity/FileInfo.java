package com.excel.entity;


import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by mqjia on 5/31/2018.
 */
@Getter
@Setter
public class FileInfo {
    private Integer id;
    private Integer userId;
    private String fileName;
    private String tableName;
    private String excelFileName;
    private String htmlFileName;
    private Date createTime;
}
