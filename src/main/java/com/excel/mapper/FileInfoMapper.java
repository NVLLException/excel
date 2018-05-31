package com.excel.mapper;

import com.excel.entity.FileInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

/**
 * Created by mqjia on 5/31/2018.
 */
@Mapper
public interface FileInfoMapper {
    @Insert("insert into fileInfo(userId,fileName,tableName,excelFileName,htmlFileName,createTime)values(#{userId},#{fileName},#{tableName},#{excelFileName},#{htmlFileName},now())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void createFileInfo(FileInfo fileInfo);
}
