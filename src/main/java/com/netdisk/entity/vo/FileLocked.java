package com.netdisk.entity.vo;

import lombok.Data;

/**
 * @author monody
 * @date 2022/3/19 9:00 下午
 */
@Data
public class FileLocked {
    String username;
    String message;
    String fileLink;
}
