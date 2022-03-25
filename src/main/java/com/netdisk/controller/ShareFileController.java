package com.netdisk.controller;

import com.netdisk.entity.File;
import com.netdisk.entity.User;
import com.netdisk.entity.bo.SharedFile;
import com.netdisk.entity.dto.UserFiles;
import com.netdisk.entity.response.BaseResponse;
import com.netdisk.entity.response.BaseResponseEntity;
import com.netdisk.entity.vo.FileLocked;
import com.netdisk.service.FileService;
import com.netdisk.service.ShareFileService;
import com.netdisk.service.UserService;
import com.netdisk.utils.ShareFileCheck;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;

/**
 * @author monody
 * @date 2022/3/17 10:37 下午
 */
@RequestMapping("/file")
@Controller
@Slf4j
//@PropertySource(value = {"classpath:server.properties"})
@PropertySource(value = {"classpath:server-server.properties"})
public class ShareFileController {

    @Resource(description = "fileService")
    private FileService fileService;

    @Resource(description = "userService")
    private UserService userService;

    @Resource(description = "shareFileService")
    private ShareFileService shareFileService;


    @Value("${server.httpType}")
    private String httpType;

    @Value("${server.domain}")
    private String serverDomain;

    @Value("${server.applicationName}")
    private String serverApplicationName;

    @Value("${server.port}")
    private String serverPort;

    @Value("${server.shareFilePath}")
    private String shareFilePath;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return "forward:/upload/admin/default.png";
    }


    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "String", required = true),
            @ApiImplicitParam(name = "token", value = "密钥", dataType = "String", required = true),
            @ApiImplicitParam(name = "day", value = "有效天数", dataType = "int", required = true),
            @ApiImplicitParam(name = "code", value = "提取码", dataType = "String", required = true),
            @ApiImplicitParam(name = "nickname", value = "昵称", dataType = "String", required = true)
    })
    @RequestMapping(value = "/share", method = RequestMethod.POST)
    public BaseResponseEntity share(@ApiIgnore User user, String code, String[] fileId, int day, String nickname) throws IOException {
        if (!ShareFileCheck.checkDayLegal(day)) {
            return BaseResponse.fail("非法分享天数");
        }
        if (userService.checkToken(user)) {
            // 判断当前文件是否为当前用户所有
            List<String> realFileId = shareFileService.checkFilesOwner(new UserFiles() {{
                setFileId(fileId);
                setUsername(user.getUsername());
            }});
            log.debug("真实存在的id集合：{}", realFileId);
            if (realFileId.size() == 0) {
                return BaseResponse.fail("分享失败");
            }
            String link = shareFileService.add(user.getUsername(), realFileId, code, day);
            if (link != null) {
                return BaseResponse.success(new HashMap<String, Object>(1) {{
                    String webLink = httpType + "://" + serverDomain + ":" + serverPort + "/" + serverApplicationName + "/" + shareFilePath + "/" + link;
                    Calendar expirationTime = Calendar.getInstance();
                    expirationTime.setTime(new Date());
                    expirationTime.add(Calendar.DATE, day);
                    SimpleDateFormat sdf = new SimpleDateFormat();
                    sdf.applyPattern("yyyy-MM-dd HH:mm:ss"); //设置显示时间格式
                    String expTime = sdf.format(expirationTime.getTime());
                    String content = String.format("%s 给您分享了一些文件，请在 %s 前查看。请点击以下链接 %s 并使用提取码 %s 进行访问。",
                            nickname, expTime, webLink, code);
                    log.debug("shareLog：{}", content);
                    put("content", content);
                }});
            }
        }
        return BaseResponse.accessDenied("身份验证失败，请重新登录");
    }

    @RequestMapping(value = "/share/get/{fileLink}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable(required = true, name = "fileLink") String fileLink, String code,
                             @ApiIgnore ModelAndView modelAndView, @ApiIgnore HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        log.debug("fileLink: {} ; code: {}", fileLink, code);
        SharedFile sharedFile = shareFileService.getFile(fileLink);
        log.debug("redis中查询到的文件信息 {}", sharedFile);
        // 检索到分享的文件
        if (sharedFile != null) {
            // 直接进入文件信息页面
            if (sharedFile.getCode() == null || sharedFile.getCode().equals(code)) {
                List<File> files = fileService.getSomeFilesByIds(sharedFile.getFileId());
                String token = shareFileService.generateToken(sharedFile.getFileId());
                modelAndView.addObject("token", token);
                modelAndView.addObject("files", files);
                modelAndView.setViewName("forward:/filelist.jsp");
                return modelAndView;
            }
            // 进入输入文件提取码页面
            FileLocked fileLocked = new FileLocked() {{
                setUsername(sharedFile.getUsername());
                setFileLink(sharedFile.getFileLink());
                setMessage("");
            }};
            if (code != null && !sharedFile.getCode().equals(code)) {
                log.debug("提取码不正确");
                fileLocked.setMessage("提取码不正确");
            }
            modelAndView.addObject("fileLocked", fileLocked);
            modelAndView.setViewName("forward:/fileLocked.jsp");

        } else {
            modelAndView.setViewName("forward:/sharedFileNotFound.html");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/share/download/{fileId}", method = {RequestMethod.GET})
    public ModelAndView download(@PathVariable String fileId, @RequestParam String token,
                                 @ApiIgnore ModelAndView modelAndView, @ApiIgnore HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        String filePath = shareFileService.downloadFile(token, fileId);
        log.debug("file = {}", filePath);
        if (filePath != null) {
            // 注意：若系统运行在Windows下必须将文件分隔符'\'替换成'/'，进行转发时才能被识别为绝对路径
            filePath = filePath.replaceAll("\\\\", Matcher.quoteReplacement("/"));
            String viewName = "forward:"  + filePath.substring(serverApplicationName.length() + 1);
            log.debug("viewName = {}", viewName);
            modelAndView.setViewName(viewName);
        } else {
            return null;
//            modelAndView.setViewName("forward:/timeout.html");
        }
        return modelAndView;
    }
}
