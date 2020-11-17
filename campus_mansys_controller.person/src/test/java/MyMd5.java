import org.junit.Test;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MyMd5 {

    /**
     * 功能：获取指定字符串的MD5值
     *
     * @param plainText    原字符串内容
     * @param returnLength 生成MD5值后 要返回的长度
     * @return
     */
    static String getMd5(String plainText, int returnLength) {
        //定义一个字节数组
        byte[] secretBytes = null;
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            //对字符串进行加密
            md.update(plainText.getBytes());
            //获得加密后的数据
            secretBytes = md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        //将加密后的数据转换为16进制数字
        String md5code = new BigInteger(1, secretBytes).toString(16);
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        // 返回指定长度
        if (returnLength > 0 && returnLength <= 32) {
            md5code = md5code.substring(0, returnLength);
        }
        return md5code;
    }

    @Test
    public void testGetMd5() {
        System.out.println(getMd5("123", 32));// 43b0
        String md5 = getMd5("123", 32);
        boolean matches = md5.matches("123");
        System.out.println(matches);
    }
}