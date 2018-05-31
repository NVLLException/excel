package com.excel.service;

import com.excel.entity.FileInfo;
import com.excel.mapper.FileInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mqjia on 5/31/2018.
 */
@Service
public class FileInfoService {
    @Autowired
    private FileInfoMapper mapper;

    @Transactional
    public FileInfo createFileInfo(FileInfo fileInfo){
        mapper.createFileInfo(fileInfo);
        return fileInfo;
    }
}
