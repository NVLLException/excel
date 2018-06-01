package com.excel.service;

import java.util.List;
import java.util.Map;

import com.excel.entity.FileInfo;
import com.excel.mapper.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mqjia on 5/31/2018.
 */
@Service
public class DataService {
    @Autowired
    private DataMapper mapper;

    @Transactional
    public FileInfo createFileInfo(FileInfo fileInfo){
        mapper.createFileInfo(fileInfo);
        return fileInfo;
    }

    @Transactional
    public void executeSql(String sql){
        mapper.executeSql(sql);
    }

    @Transactional
    public List executeQuerySql(String sql){
        return mapper.executeQuerySql(sql);
    }
}
