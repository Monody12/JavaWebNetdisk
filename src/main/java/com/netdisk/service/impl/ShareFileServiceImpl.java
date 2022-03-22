package com.netdisk.service.impl;

import com.netdisk.entity.File;
import com.netdisk.entity.bo.SharedFile;
import com.netdisk.entity.dto.UserFiles;
import com.netdisk.mapper.ShareFileServiceMapper;
import com.netdisk.service.FileService;
import com.netdisk.service.ShareFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author monody
 * @date 2022/3/17 11:11 下午
 */
@Slf4j
public class ShareFileServiceImpl implements ShareFileService {

    private RedisTemplate<String, SharedFile> sharedFileRedisTemplate;

    public void setSharedFileRedisTemplate(RedisTemplate<String, SharedFile> sharedFileRedisTemplate) {
        this.sharedFileRedisTemplate = sharedFileRedisTemplate;
    }

    private RedisTemplate<String, String> stringRedisTemplate;

    public void setStringRedisTemplate(RedisTemplate<String, String> stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    private RedisTemplate<String, List> listRedisTemplate;

    public void setListRedisTemplate(RedisTemplate<String, List> listRedisTemplate) {
        this.listRedisTemplate = listRedisTemplate;
    }

    private FileService fileService;

    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    private ShareFileServiceMapper shareFileServiceMapper;

    public void setShareFileServiceMapper(ShareFileServiceMapper shareFileServiceMapper) {
        this.shareFileServiceMapper = shareFileServiceMapper;
    }

    @Override
    public String add(String username, List<String> fileId, String code, int day) {
        ValueOperations<String, SharedFile> op = sharedFileRedisTemplate.opsForValue();
        String fileLink = username + "-" + UUID.randomUUID();
        SharedFile sharedFile = new SharedFile() {{
            setUsername(username);
            setFileId(fileId);
            setCode(code);
            setFileLink(fileLink);
        }};
        op.set(fileLink, sharedFile, day, TimeUnit.DAYS);
        SharedFile file = op.get(fileLink);
        log.debug("生成的分享文件信息为：{}", file);
        return fileLink;
    }

    @Override
    public String generateToken(List<String> fileId) {
        ValueOperations<String, List> ops = listRedisTemplate.opsForValue();
        String uuid = "T-" + UUID.randomUUID().toString();
        ops.set(uuid, fileId, 10, TimeUnit.MINUTES);
        return uuid;
    }

    @Override
    public String downloadFile(String token, String fileId) {
        if (token.charAt(0) == 'T') {
            ValueOperations<String, List> ops = listRedisTemplate.opsForValue();
            List list = ops.get(token);
            if (list != null && list.contains(fileId)) {
                File file = fileService.findFileInfoById(new File(fileId));
                return file.getPath();
            }
        }
        return null;
    }


    @Override
    public SharedFile getFile(String fileLink) {
        ValueOperations<String, SharedFile> op = sharedFileRedisTemplate.opsForValue();
        return op.get(fileLink);
    }

    @Override
    public boolean delete(String fileLink) {
        return false;
    }

    @Override
    public List<String> checkFilesOwner(UserFiles userFiles) {
        return shareFileServiceMapper.checkFilesOwner(userFiles);
    }
}
