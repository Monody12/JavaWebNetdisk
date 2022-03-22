package com.netdisk.entity.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Be_Young
 * @Date: 2021/5/20 22:37
 */
@Data
public class BaseResponseEntity implements Serializable {

    private int code;

    private String msg;

    private Object resData;

}
