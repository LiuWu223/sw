package com.lw.swTest.core.utils;
import java.security.MessageDigest;
import java.util.Random;
import org.apache.commons.codec.binary.Hex;

public class MD5Util {
    /**
     * &#064;category  获取盐值
     * @return 盐
     */
    public static String getSalt(){
        // 生成一个16位的随机数
        char[] code =  "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            sb.append(code[new Random().nextInt(code.length)]);
        }
        // 生成最终的加密盐
        String Salt = sb.toString();
        return Salt;
    }
    /**
     * &#064;category  加盐MD5加密
     * @param password 密码
     * @param Salt 盐
     * @return 加密密码
     */
    public static String getSaltMD5(String password,String Salt) {
        password = md5Hex(password + Salt);
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            cs[i] = password.charAt(i / 3 * 2);
            char c = Salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = password.charAt(i / 3 * 2 + 1);
        }
        return String.valueOf(cs);
    }
    /**
     * &#064;category  使用Apache的Hex类实现Hex(16进制字符串和)和字节数组的互转
     * @param str 字符串
     * @return 转换后的结果
     */
    private static String md5Hex(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(str.getBytes());
            return new String(new Hex().encode(digest));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
            return "";
        }
    }

    /**
     * &#064;category  判断密码是否一致
     * @param password 密码
     * @param md5str 加密过的密码
     * @return 判断结果
     */
    public static boolean getSaltverifyMD5(String password,String md5str,String Salt) {
        return md5str.equals(getSaltMD5(password,Salt));
    }

    public static void main(String[] args) {
        String salt = getSalt();
        String md5 = getSaltMD5("123",salt);
        boolean logs = getSaltverifyMD5("123",md5,salt);
        System.out.println("salt:" + salt + ",md5:" + md5 + "登录结果：" + logs);

    }
}
