package com.defectscan.utils;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SafeUtil {
    @Value("${common.utils.safeFactor}")
    private int factor;
    /**
     * 生成密码的bcrypt哈希值
     *
     * @param password 用户输入的密码
     * @return 加密后的哈希值
     */
    public String hashPassword(String password) {
        // 这里的12是工作因子（work factor），可以根据需要调整，数值越大，计算越慢，但安全性越高
        return BCrypt.hashpw(password, BCrypt.gensalt(factor));
    }

    /**
     * 验证密码是否匹配哈希值
     *
     * @param rawPassword 用户输入的密码
     * @param hashedPassword 存储的哈希值
     * @return 如果密码匹配，则返回true；否则返回false
     */
    public static boolean checkPassword(String rawPassword, String hashedPassword) {
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }

    public static void main(String[] args) {

    }
}

