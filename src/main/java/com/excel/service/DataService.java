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

    public List<Map> retrieveFileInfoGroupByUser(String tableName){
        return mapper.retrieveFileInfoGroupByUser(tableName);
    }

    public List<Map> retrieveFileInfoDataByUserId(String tableName, String userId){
        return mapper.retrieveFileInfoDataByUserId(tableName, userId);
    }

    public List<User> retrieveUser(String id){
        return mapper.retrieveUserById(Integer.valueOf(id));
    }

    public List<User> retrieveUser(String loginName, String password){
        return mapper.retrieveUser(loginName,password);
    }

    @Transactional
    public void executeSql(String sql){
        mapper.executeSql(sql);
    }

    @Transactional
    public List executeQuerySql(String sql){
        return mapper.executeQuerySql(sql);
    }

    public List getModuleList(){
        return mapper.retrieveModuleList();
    }

    public void addModule(String moduleName){
        mapper.addModule(moduleName);
    }

    public void deleteModule(String id){
        mapper.deleteModule(id);
    }
}
