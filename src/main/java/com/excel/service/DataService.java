package com.excel.service;

import java.util.List;
import java.util.Map;

import com.excel.entity.FileInfo;
import com.excel.entity.User;
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

    public List<Map> checkLogin(String loginName, String password){
        return mapper.checkLogin(loginName, password);
    }

    public Long checkLoginName(String loginName){
        return mapper.checkLoginName(loginName);
    }

    @Transactional
    public void createUser(User  user){
        mapper.createUser(user);
    }

    public List<Map> retrieveAllFileInfo(){
        return mapper.retrieveAllFileInfo();
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
