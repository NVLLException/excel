package com.excel.mapper;

import com.excel.entity.FileInfo;
import com.excel.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by mqjia on 5/31/2018.
 */
@Mapper
public interface DataMapper {
    @Insert("insert into fileInfo(userId,fileName,tableName,excelFileName,htmlFileName,createTime)values(#{userId},#{fileName},#{tableName},#{excelFileName},#{htmlFileName},now())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void createFileInfo(FileInfo fileInfo);

    @Select("select * from user where loginName=#{loginName} and password=#{password}")
    public List<Map> checkLogin(String loginName, String password);

    @Select("select count(*) as count from user where loginName=#{lognName}")
    @ResultType(Long.class)
    public Long checkLoginName(String loginName);

    @Insert("insert into user (loginName,nikeName,password,createTime)values(#{loginName},#{nikeName},#{password},now())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public User createUser(User user);

    @Update("${sql}")
    public void executeSql(@Param("sql") String sql);

    @Select("${sql}")
    @ResultType(List.class)
    public List<Map> executeQuerySql(@Param("sql") String sql);
}
