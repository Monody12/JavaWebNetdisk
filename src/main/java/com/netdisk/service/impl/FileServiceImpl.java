package com.netdisk.service.impl;

import com.netdisk.entity.File;
import com.netdisk.entity.User;
import com.netdisk.mapper.FileMapper;
import com.netdisk.service.FileService;

import java.util.List;

public class FileServiceImpl implements FileService {

    private FileMapper fileMapper;

    public void setFileMapper(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    @Override
    public List<File> findAllByUsername(User user) {
        return fileMapper.findAllByUsername(user);
    }

    @Override
    public File findFileInfoById(File file) {
        return fileMapper.findFileInfoById(file);
    }

    @Override
    public List<File> findPublicFile() {
        return fileMapper.findPublicFile();
    }

    @Override
    public int updateFileInfo(File file) {
        return fileMapper.updateFileInfo(file);
    }

    @Override
    public int insertFileInfo(File file) {
        return fileMapper.insertFileInfo(file);
    }

    @Override
    public int deleteFileInfo(File file) {
        return fileMapper.deleteFileInfo(file);
    }

    @Override
    public File findFileInfoByPath(File file) {
        return fileMapper.findFileInfoById(file);
    }
}
