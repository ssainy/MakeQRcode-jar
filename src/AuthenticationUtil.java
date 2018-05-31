import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by thinkpad on 2017/11/29.
 */
public class AuthenticationUtil {


    // 加密
    public static String Encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));

        return new Base64().encodeToString(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    // 解密
    public static String Decrypt(String sSrc, String sKey) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = new Base64().decode(sSrc);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,"utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public static String authentication(String key,String data){
        try {
            String cSrc =EncoderByMd5(data);
            System.out.println("MD5之后的结果："+cSrc);

            // 加密
            String enString = Encrypt(cSrc, key);
            System.out.println("加密后的字串是：" + enString);

            return enString;
        } catch (Exception e) {
            e.printStackTrace();
            return "Fail";
        }
    }

       public static String getEncrypt(String key,String data) throws Exception {
        /*
         * 此处使用AES-128-ECB加密模式，key需要为16位。
        */
           String cSrc = EncoderByMd5(data);
           System.out.println("MD5结果为："+cSrc);

           // 加密
           String enString = Encrypt(cSrc, key);
           System.out.println("加密后的字串是：" + enString);
           return enString;
       }
    public static void main(String[] args) throws Exception {
        String enString =getEncrypt("pdNa8U37fmJz7x3H","{\n" +
                "        \"openid\": \"ooCGlv6_5Tp_l56NS7xpZzNe6I4w\",\n" +
                "        \"bizappid\": \"wxea7b63caeca0d6fa\",\n" +
                "        \"ddh\": \"201801311626\",\n" +
                "        \"fpqqlsh\": \"wangwei2018010900020\",\n" +
                "        \"nsrsbh\": \"150001197205134629\",\n" +
                "        \"nsrmc\": \"微商贸易有限公司\",\n" +
                "        \"nsrdz\": \"测试企业地址\",\n" +
                "        \"nsrdh\": \"15333333333\",\n" +
                "        \"ghfmc\": \"C Business Consulting Co.， Ltd\",\n" +
                "        \"kpr\": \"开票员一\",\n" +
                "        \"skr\": \"收款员y\",\n" +
                "        \"fhr\": \"复核人y\",\n" +
                "        \"jshj\": \"58.5\",\n" +
                "        \"hjje\": \"50.00\",\n" +
                "        \"hjse\": \"8.5\",\n" +
                "        \"bz\": \"\",\n" +
                "        \"hylx\": \"0\",\n" +
                "        \"nsrbank\": \"中国银行\",\n" +
                "        \"nsrbankid\": \"123\",\n" +
                "        \"ghfsbh\": \"150001203909248035\",\n" +
                "        \"ghfbank\": \"中国银行\",\n" +
                "        \"ghfbankkid\": \"678\",\n" +
                "        \"ghfdz\": \"中国\",\n" +
                "        \"ghfdh\": \"中国\",\n" +
                "        \"invoiceDetailList\": [\n" +
                "            {\n" +
                "                \"fphxz\": \"2\",\n" +
                "                \"spbm\": \"1010101030000000000\",\n" +
                "                \"xmmc\": \"A1\",\n" +
                "                \"dw\": \"件\",\n" +
                "                \"ggxh\": \"1.0\",\n" +
                "                \"xmsl\": \"1\",\n" +
                "                \"xmdj\": \"100\",\n" +
                "                \"xmje\": \"100\",\n" +
                "                \"sl\": \"0.17\",\n" +
                "                \"se\": \"17\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"fphxz\": \"1\",\n" +
                "                \"spbm\": \"1010101030000000000\",\n" +
                "                \"xmmc\": \"A1\",\n" +
                "                \"dw\": \"件\",\n" +
                "                \"ggxh\": \"1.0\",\n" +
                "                \"xmsl\": \"-1\",\n" +
                "                \"xmdj\": \"50\",\n" +
                "                \"xmje\": \"-50\",\n" +
                "                \"sl\": \"0.17\",\n" +
                "                \"se\": \"-8.5\"\n" +
                "            }");
        System.out.println("加密后的字串是：" + enString);
    }
/*////        // 解密
        String DeString = AesUtils.decrypt(enString, cKey);
        System.out.println("解密后的字串是：" + DeString);
    }
   public static void main(String[] args) throws Exception {

    }
    /**利用MD5进行加密
     * @param str  待加密的字符串
     * @return  加密后的字符串

     */
    public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        String result="";
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update((str).getBytes("UTF-8"));
        byte b[] = md5.digest();

        int i;
        StringBuffer buf = new StringBuffer("");

        for(int offset=0; offset<b.length; offset++){
            i = b[offset];
            if(i<0){
                i+=256;
            }
            if(i<16){
                buf.append("0");
            }
            buf.append(Integer.toHexString(i));
        }

        result = buf.toString();
        return result;
    }

    public static String SHA1(String decript) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
