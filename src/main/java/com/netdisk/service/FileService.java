package com.netdisk.service;

import com.netdisk.entity.File;
import com.netdisk.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileService {
    public List<File> findAllByUsername(User user);

    List<File> getSomeFilesByIds(@Param("fileId") List<String> fileId);

    public File findFileInfoById(File file);

    public List<File> findPublicFile();

    public int updateFileInfo(File file);

    public int insertFileInfo(File file);

    public int deleteFileInfo(File file);

    public File findFileInfoByPath(File file);

}
