package com.netdisk.entity.response;

import com.netdisk.entity.response.enums.ResponseCode;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @Author: Be_Young
 * @Date: 2021/5/20 22:31
 */
@Slf4j
public class BaseResponse {

    public static BaseResponseEntity success(Map<String, Object> resMap) {
        BaseResponseEntity responseEntity = new BaseResponseEntity();
        responseEntity.setCode(ResponseCode.Success.getCode());
        responseEntity.setMsg(ResponseCode.Success.getMsg());
        responseEntity.setResData(resMap);
        return responseEntity;
    }

    public static BaseResponseEntity success(String msg) {
        BaseResponseEntity responseEntity = new BaseResponseEntity();
        responseEntity.setCode(ResponseCode.Success.getCode());
        responseEntity.setMsg(msg);
        return responseEntity;
    }

    public static BaseResponseEntity success() {
        BaseResponseEntity responseEntity = new BaseResponseEntity();
        responseEntity.setCode(ResponseCode.Success.getCode());
        responseEntity.setMsg(ResponseCode.Success.getMsg());
        return responseEntity;
    }

    public static BaseResponseEntity fail(int code, String msg, Map<String, Object> resMap) {
        BaseResponseEntity responseEntity = new BaseResponseEntity();
        responseEntity.setCode(code);
        responseEntity.setMsg(msg);
        responseEntity.setResData(resMap);
        return responseEntity;
    }

    public static BaseResponseEntity fail(int code, String msg) {
        BaseResponseEntity responseEntity = new BaseResponseEntity();
        responseEntity.setCode(code);
        responseEntity.setMsg(msg);
        return responseEntity;
    }

    public static BaseResponseEntity fail(String msg) {
        BaseResponseEntity responseEntity = new BaseResponseEntity();
        responseEntity.setCode(ResponseCode.Fail.getCode());
        responseEntity.setMsg(msg);
        return responseEntity;
    }

    public static BaseResponseEntity fail() {
        BaseResponseEntity responseEntity = new BaseResponseEntity();
        responseEntity.setCode(ResponseCode.Fail.getCode());
        responseEntity.setMsg(ResponseCode.Fail.getMsg());
        return responseEntity;
    }

    public static BaseResponseEntity passwordError() {
        BaseResponseEntity responseEntity = new BaseResponseEntity();
        responseEntity.setCode(ResponseCode.PasswordError.getCode());
        responseEntity.setMsg(ResponseCode.PasswordError.getMsg());
        return responseEntity;
    }

    public static BaseResponseEntity userNotFound() {
        BaseResponseEntity responseEntity = new BaseResponseEntity();
        responseEntity.setCode(ResponseCode.UserNotFound.getCode());
        responseEntity.setMsg(ResponseCode.UserNotFound.getMsg());
        return responseEntity;
    }

    public static BaseResponseEntity fileNotFound() {
        BaseResponseEntity responseEntity = new BaseResponseEntity();
        responseEntity.setCode(ResponseCode.FileNotFound.getCode());
        responseEntity.setMsg(ResponseCode.FileNotFound.getMsg());
        return responseEntity;
    }

    public static BaseResponseEntity accessDenied() {
        BaseResponseEntity responseEntity = new BaseResponseEntity();
        responseEntity.setCode(ResponseCode.AccessDenied.getCode());
        responseEntity.setMsg(ResponseCode.AccessDenied.getMsg());
        return responseEntity;
    }

    public static BaseResponseEntity accessDenied(String msg) {
        BaseResponseEntity responseEntity = new BaseResponseEntity();
        responseEntity.setCode(ResponseCode.AccessDenied.getCode());
        responseEntity.setMsg(msg);
        return responseEntity;
    }

    public static BaseResponseEntity tokenInvalid() {
        BaseResponseEntity responseEntity = new BaseResponseEntity();
        responseEntity.setCode(ResponseCode.TokenInvalid.getCode());
        responseEntity.setMsg(ResponseCode.TokenInvalid.getMsg());
        return responseEntity;
    }
}
