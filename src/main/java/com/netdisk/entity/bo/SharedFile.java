package com.netdisk.entity.bo;


import java.util.List;


/**
 * @author monody
 * @date 2022/3/17 10:54 下午
 */

public class SharedFile {
    String fileLink;
    String code;
    String username;
    List<String> fileId;

    public String getFileLink() {
        return fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getFileId() {
        return fileId;
    }

    public void setFileId(List<String> fileId) {
        this.fileId = fileId;
    }

    @Override
    public String toString() {
        return "SharedFile{" +
                "fileLink='" + fileLink + '\'' +
                ", code='" + code + '\'' +
                ", username='" + username + '\'' +
                ", fileId=" + fileId +
                '}';
    }

}
