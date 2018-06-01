package com.excel.mapper;

import com.excel.entity.FileInfo;
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

    @Update("${sql}")
    public void executeSql(@Param("sql") String sql);

    @Select("${sql}")
    @ResultType(List.class)
    public List<Map> executeQuerySql(@Param("sql") String sql);
}
