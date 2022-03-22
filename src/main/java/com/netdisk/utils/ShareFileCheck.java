package com.netdisk.utils;

/**
 * @author monody
 * @date 2022/3/19 12:07 上午
 */
public class ShareFileCheck {
    public static boolean checkDayLegal(int day) {
        return day == 1 || day == 3 || day == 7 || day == 30;
    }
}
