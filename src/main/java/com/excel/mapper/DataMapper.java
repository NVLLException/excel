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
    @Insert("insert into fileInfo(moduleId,userId,fileName,tableName,excelFileName,htmlFileName,createTime)values(#{moduleId},#{userId},#{fileName},#{tableName},#{excelFileName},#{htmlFileName},now())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void createFileInfo(FileInfo fileInfo);

    @Select("select * from user where loginName=#{loginName} and password=#{password}")
    @ResultType(List.class)
    public List<Map> checkLogin(@Param("loginName") String loginName, @Param("password") String password);

    @Select("select count(*) as count from user where loginName=#{lognName}")
    @ResultType(Long.class)
    public Long checkLoginName(String loginName);

    @Insert("insert into user (loginName,nickName,password,createTime, isAdmin)values(#{loginName},#{nickName},#{password},now(),#{isAdmin})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void createUser(User user);

    @Select("select * from fileinfo where moduleId=#{moduleId}")
    @ResultType(List.class)
    public List<Map> retrieveAllFileInfo(@Param("moduleId") String moduleId);

    @Select(" select count(*) as count,user.id, user.nickName from ${tableName} left join user on  ${tableName}.userId = user.id   group by ${tableName}.userId")
    @ResultType(List.class)
    public List<Map> retrieveFileInfoGroupByUser(@Param("tableName") String tableName);

    @Select(" select count(*) as count,user.id, user.nickName from ${tableName} left join user on  ${tableName}.userId = user.id  where ${tableName}.userId =#{userId} group by ${tableName}.userId ")
    @ResultType(List.class)
    public List<Map> retrieveFileInfoByUserId(@Param("tableName") String tableName, @Param("userId") String userId);

    @Select("select * from ${tableName} where userId=#{userId}")
    @ResultType(List.class)
    public List<Map> retrieveFileInfoDataByUserId(@Param("tableName") String tableName, @Param("userId") String userId);

    @Select("select * from user where id=#{id}")
    @ResultType(List.class)
    public List<User> retrieveUserById(@Param("id") Integer id);

    @Select("select * from user where loginName=#{loginName} and password=#{password}")
    @ResultType(List.class)
    public List<User> retrieveUser(@Param("loginName") String loginName, @Param("password") String password);

    @Select("select * from fileInfo where id=#{id}")
    @ResultType(List.class)
    public List<FileInfo> retrieveFileInfo(@Param("id") String id);

    @Update("${sql}")
    public void executeSql(@Param("sql") String sql);

    @Select("${sql}")
    @ResultType(List.class)
    public List<Map> executeQuerySql(@Param("sql") String sql);

    @Select("select * from module")
    @ResultType(List.class)
    public List<Map> retrieveModuleList();

    @Select("select module.* from module left join permission on module.id = permission.moduleId where permission.userId=#{userId}")
    @ResultType(List.class)
    public List<Map> retrieveModuleListByUserId(@Param("userId") String userId);

    @Insert("insert into module(`name`)values(#{moduleName})")
    public void addModule(@Param("moduleName") String moduleName);

    @Delete("delete from module where id=#{id}")
    public void deleteModule(@Param("id") String id);
}
