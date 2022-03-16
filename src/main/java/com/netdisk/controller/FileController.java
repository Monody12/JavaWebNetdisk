package com.netdisk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netdisk.entity.File;
import com.netdisk.entity.User;
import com.netdisk.service.FileService;
import com.netdisk.service.UserService;
import com.netdisk.utils.GetFileType;
import com.netdisk.utils.SnowflakeIdWorker;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequestMapping("/file")
@Controller
public class FileController {

    @Resource(description = "fileService")
    private FileService fileService;

    @Resource(description = "userService")
    private UserService userService;

    private static final SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0, 0);

    private static final GetFileType getFileType = new GetFileType();

    @RequestMapping("/get/self")
    @ResponseBody
    public String getSelfFiles(User user, HttpServletResponse response) throws JsonProcessingException {
        if (userService.checkToken(user)) {
            List<File> fileList = fileService.findAllByUsername(user);
            ObjectMapper objectMapper = new ObjectMapper();
            response.setContentType("text/plain");  //设置为纯文本格式
            return objectMapper.writeValueAsString(fileList);
        }
        return "{}";
    }

    @RequestMapping("/get/public")
    @ResponseBody
    public String getPublicFiles(User user, boolean sort) throws JsonProcessingException {
        if (userService.checkToken(user)) {
            List<File> fileList = fileService.findPublicFile();
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(fileList);
        }
        return "{}";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(HttpServletRequest request, User user, String[] ids) {
        int cnt = 0;
        if (userService.checkToken(user)) {  //验证用户token
            File tmp = new File();
            for (String id : ids) {
                tmp.setId(id);
                File file = fileService.findFileInfoById(tmp);  //查找文件信息
                if (file != null && file.getUsername().equals(user.getUsername())) {  //文件所有者才能删除文件 并且需要从磁盘中删除
                    (new java.io.File(request.getServletContext().getRealPath("") + file.getPath().substring(request.getContextPath().length()))).delete();
                    cnt += fileService.deleteFileInfo(tmp);
                } else
                    return "-1";
            }
        } else
            return "-1";
        return String.valueOf(cnt);
    }

    @RequestMapping("/upload")
    public ModelAndView upload(ModelAndView modelAndView, HttpServletRequest request, User user, MultipartFile[] files, String detail, boolean isPublic, boolean isOverwrite) throws IOException {
        if (userService.checkToken(user)) {
            String separator = java.io.File.separator;
            String userDir = "upload" + separator + user.getUsername();
            String path = request.getServletContext().getRealPath("") + userDir + separator;  //文件存放真实路径
            java.io.File fileDir = new java.io.File(path); //查找文件存放路径是否存在并生成
            if (!fileDir.exists())
                fileDir.mkdirs();
            File newFile = new File();
            newFile.setUsername(user.getUsername());
            newFile.setPublic(isPublic);
            newFile.setDetail(detail);
            int cnt = 0, exist_cnt = 0, overwrite_cnt = 0;
            List<String> err_file = new ArrayList<>();  //未上传文件
            List<String> overwrite_file = new ArrayList<>();  //被覆盖文件
            for (MultipartFile file : files) {
                newFile.setPath(request.getContextPath() + separator + userDir + separator + file.getOriginalFilename());  //存入数据库时只需要存相对路径
                newFile.setId(String.valueOf(snowflakeIdWorker.nextId()));
                newFile.setName(file.getOriginalFilename());
                newFile.setSize(file.getSize());
                newFile.setType(getFileType.getFileType(Objects.requireNonNull(file.getOriginalFilename())));
                if (newFile.getType().length() >= 18)
                    newFile.setType("文件");
                try {
                    cnt += fileService.insertFileInfo(newFile);
                } catch (org.springframework.dao.DuplicateKeyException e) {  //如果触发主键约束（路径下文件不唯一），则触发此异常
                    if (!isOverwrite) {
                        ++exist_cnt;
                        err_file.add(newFile.getName());
                        continue;
                    } else {  //覆盖同名文件
                        ++overwrite_cnt;
                        File tmp = new File();
                        tmp.setPath(newFile.getPath());
                        overwrite_file.add(newFile.getName());
                        fileService.deleteFileInfo(tmp);
                        cnt += fileService.insertFileInfo(newFile);
                    }
                }
                file.transferTo(new java.io.File(path + file.getOriginalFilename()));  //将文件写入磁盘
            }
            modelAndView.addObject("isOverwrite", isOverwrite);
            modelAndView.addObject("message", "文件上传结果");
            modelAndView.addObject("all_count", files.length);
            modelAndView.addObject("exist_cnt", exist_cnt);
            modelAndView.addObject("overwrite_cnt", overwrite_cnt);
            modelAndView.addObject("count", cnt);
            modelAndView.addObject("err_file", err_file);
            modelAndView.addObject("overwrite_file", overwrite_file);

            modelAndView.setViewName("forward:/message.jsp");
        }else  //用户信息校验错误，跳转到登录页面
            modelAndView.setViewName("redirect:/index.html");

        return modelAndView;
    }


    @RequestMapping("/update")
    @ResponseBody
    public String update(User user, File file, boolean isPublic) {
        file.setPublic(isPublic);
        File fileInfo = fileService.findFileInfoById(file);
        User fileUser = new User();
        fileUser.setUsername(fileInfo.getUsername());
        fileUser = userService.findUserByUsername(fileUser);

        if (fileUser.getToken().equals(user.getToken())) {  //校验文件所有者
            return String.valueOf(fileService.updateFileInfo(file));
        } else
            return "-1";
    }
}
