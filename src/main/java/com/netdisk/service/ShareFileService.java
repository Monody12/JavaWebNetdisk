package com.netdisk.service;

import com.netdisk.entity.bo.SharedFile;
import com.netdisk.entity.dto.UserFiles;

import java.util.List;

/**
 * @author monody
 * @date 2022/3/17 11:07 下午
 */
public interface ShareFileService {

    /**
     * 新增分享文件
     * @param username 用户名
     * @param fileId 文件id
     * @param code 提取码
     * @param day 有效天数
     * @return 分享链接
     */
    String add(String username, List<String> fileId, String code, int day);

    /**
     * 生成临时token给受分享用户
     * 生成一个UUID在redis中，持续时间为10分钟
     * @param fileId 该token允许下载的文件列表
     * @return uuid token
     */
    String generateToken(List<String> fileId);


    /**
     * 下载文件
     * 校验下载文件临时token是否有效
     * @param fileId 想要下载的文件id
     * @param token token
     * @return 文件路径（校验失败返回null）
     */
    String downloadFile(String token, String fileId);

    /**
     * 根据链接获取分享的文件信息
     * @param fileLink 文件链接
     * @return 分享文件信息
     */
    SharedFile getFile(String fileLink);


    /**
     * 取消分享文件
     * @param fileLink 文件链接
     * @return 是否成功
     */
    boolean delete(String fileLink);

    /**
     * 一些文件id中找出属于该用户的文件id
     * @param userFiles 一些文件id和用户名
     * @return 属于该用户的文件id
     */
    List<String> checkFilesOwner (UserFiles userFiles);

}
