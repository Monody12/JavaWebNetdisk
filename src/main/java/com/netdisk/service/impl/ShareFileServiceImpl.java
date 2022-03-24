package com.netdisk.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netdisk.entity.File;
import com.netdisk.entity.bo.SharedFile;
import com.netdisk.entity.dto.UserFiles;
import com.netdisk.mapper.ShareFileServiceMapper;
import com.netdisk.service.FileService;
import com.netdisk.service.ShareFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.IOException;
import java.text.ParseException;
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

    private ObjectMapper objectMapper;

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String add(String username, List<String> fileId, String code, int day) throws IOException {
        ValueOperations<String, String> op = stringRedisTemplate.opsForValue();
        String fileLink = username + "-" + UUID.randomUUID();

        SharedFile sharedFile = new SharedFile() {{
            setUsername(username);
            setFileId(fileId);
            setCode(code);
            setFileLink(fileLink);
        }};
        String s = objectMapper.writeValueAsString(sharedFile);
        op.set(fileLink, s, day, TimeUnit.DAYS);
        String o = op.get(fileLink);
        SharedFile value = objectMapper.readValue(o, SharedFile.class);
        log.debug("生成的分享文件信息为：{}", value);
        return fileLink;
    }

    @Override
    public String generateToken(List<String> fileId) {
        ValueOperations<String, List> ops = listRedisTemplate.opsForValue();
        String uuid = "T-" + UUID.randomUUID().toString();
        ops.set(uuid, fileId, 5, TimeUnit.MINUTES);
        return uuid;
    }

    @Override
    public String downloadFile(String token, String fileId) {
        // 验证传入token的合法性
        if (token != null && token.charAt(0) == 'T' && stringRedisTemplate.opsForValue().get(token) != null) {
            // 查询这个下载的文件是否在分享列表中
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
    public SharedFile getFile(String fileLink) throws IOException {
        log.debug("getFile : {}", fileLink);
        ValueOperations<String, String> op = stringRedisTemplate.opsForValue();
        String s = op.get(fileLink);
        SharedFile sharedFile = objectMapper.readValue(s, SharedFile.class);
        return sharedFile;
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
