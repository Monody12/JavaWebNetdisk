package com.netdisk.mapper;

import com.netdisk.entity.dto.UserFiles;

import java.util.List;

/**
 * @author monody
 * @date 2022/3/20 8:33 下午
 */
public interface ShareFileServiceMapper {


    List<String> checkFilesOwner (UserFiles userFiles);
}
