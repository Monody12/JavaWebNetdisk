package com.netdisk.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputCheck {
    public static boolean usernameCheck(String username) {
        String pattern = "^\\w{4,18}$";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(username);
        return m.matches();
    }

    public static boolean passwordCheck(String password) {
        String pattern = "^[A-Za-z0-9]{4,8}$";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(password);
        return m.matches();
    }

    public static boolean nicknameCheck(String nickname) {
        String pattern = "^[\\u4E00-\\u9FA5A-Za-z0-9]{1,15}$";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(nickname);
        return m.matches();
    }
}
