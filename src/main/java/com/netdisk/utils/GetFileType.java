package com.netdisk.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GetFileType {
    private Map<String, Set<String>> map;

    public GetFileType() {
        map = new HashMap();
        Set<String> photo = new HashSet();
        photo.add("jpg");
        photo.add("jpeg");
        photo.add("png");
        photo.add("bmp");
        photo.add("gif");
        map.put("图片", photo);

        Set<String> music = new HashSet();
        music.add("mp3");
        music.add("wav");
        music.add("ogg");
        music.add("ape");
        map.put("音乐", music);

        Set<String> document = new HashSet();
        document.add("doc");
        document.add("docx");
        document.add("xls");
        document.add("xlsx");
        document.add("ppt");
        document.add("pptx");
        document.add("txt");
        document.add("pdf");
        document.add("md");
        map.put("文档", document);

        Set<String> compressed = new HashSet();
        compressed.add("zip");
        compressed.add("rar");
        compressed.add("7z");
        map.put("压缩文件", compressed);

        Set<String> video = new HashSet();
        video.add("mp4");
        video.add("rmvb");
        video.add("flv");
        video.add("avi");
        video.add("3gp");
        video.add("wmv");
        map.put("视频", video);

        Set<String> application = new HashSet();
        application.add("exe");
        application.add("msi");
        application.add("app");
        application.add("apk");
        application.add("rpm");
        application.add("deb");
        application.add("gz");
        application.add("dmg");
        map.put("应用程序", application);

    }

    public String getFileType(String fileName) {

        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        for (String key : map.keySet()) {
            HashSet<String> set = (HashSet<String>) map.get(key);
            if (set.contains(suffix))
                return key;
        }
        return suffix.toUpperCase() + "文件";
    }
}
