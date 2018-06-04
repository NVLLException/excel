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
    @ResultType(List.class)
    public List<Map> checkLogin(@Param("loginName") String loginName, @Param("password") String password);

    @Select("select count(*) as count from user where loginName=#{lognName}")
    @ResultType(Long.class)
    public Long checkLoginName(String loginName);

    @Insert("insert into user (loginName,nikeName,password,createTime)values(#{loginName},#{nikeName},#{password},now())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void createUser(User user);

    @Select("select * from fileinfo")
    @ResultType(List.class)
    public List<Map> retrieveAllFileInfo();

    @Select("select count(*) as count,fileinfo.userId from fileinfo where id=#{formId} group by userId")
    @ResultType(List.class)
    public List<Map> retrieveFileInfoGroupByUser(@Param("formId") String formId);

    @Select("select * from ${tableName} where userId=#{userId}")
    @ResultType(List.class)
    public List<Map> retrieveFileInfoDataByUserId(@Param("tableName") String tableName, @Param("userId") String userId);

    @Select("select * from user where id=#{id}")
    @ResultType(List.class)
    public List<User> retrieveUserById(@Param("id") Integer id);

    @Select("select * from user where loginName=#{loginName} and password=#{password}")
    @ResultType(List.class)
    public List<User> retrieveUser(@Param("loginName") String loginName, @Param("password") String password);

    @Update("${sql}")
    public void executeSql(@Param("sql") String sql);

    @Select("${sql}")
    @ResultType(List.class)
    public List<Map> executeQuerySql(@Param("sql") String sql);
}
