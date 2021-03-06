package com.netdisk.mapper;

import com.netdisk.entity.File;
import com.netdisk.entity.User;

import java.util.List;

public interface FileMapper {
    public List<File> findAllByUsername(User user);

    public List<File> getSomeFilesByIds(List<String> fileId);

    public File findFileInfoById(File file);

    public List<File> findPublicFile();

    public int updateFileInfo(File file);

    public int insertFileInfo(File file);

    public int deleteFileInfo(File file);

    public File findFileInfoByPath(File file);
}
