import org.apache.commons.codec.digest.DigestUtils;

/**
 * Description:MD5测试类
 */
public class MD5Test{

    public static void main(String[] args) {

        String keyword="123";
        String md5= DigestUtils.md5Hex(keyword);
        System.out.println("md5加密后："+"\n"+md5);
        String md5salt= MD5.md5PlusSalt(keyword);
        System.out.println("加盐后："+"\n"+md5salt);
        String word= MD5.md5MinusSalt(md5salt);
        System.out.println("解密后："+"\n"+word);
        boolean matches = md5.matches(word);
        System.out.println(matches);
    }
}