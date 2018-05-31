import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by cch on 2018/1/15.
 */
public class GenerateSigna {
    /**
     *  生产签名串
     * @param srcSignString
     *              原始签名字符串
     * @param clientSecret
     *              加密串
     * @return
     *          base64的签名字符串
     * @throws Exception
     */
    public static final String CHARSET = "UTF-8";
    private static final String HMAC_SHA1 = "HmacSHA1";
    public static String generateSigna(String srcSignString, String clientSecret) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(clientSecret.getBytes(CHARSET), HMAC_SHA1);
        Mac mac = Mac.getInstance(HMAC_SHA1);
        mac.init(keySpec);
        byte[] signBytes = mac.doFinal(srcSignString.getBytes(CHARSET));
        String signStr = new String(Base64Util.encode(signBytes));
        return signStr;
    }
}
