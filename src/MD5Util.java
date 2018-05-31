/**
 * Copyright (c) 2015（年）,北京蓝威技术有限公司
 * All rights reserved.
 *
 * MD5Util.java
 * 描述：
 *
 * 当前版本：1.0
 *
 * 创建时间：2015年7月18日
 * 作者: 郑梓鸿
 */


import java.security.MessageDigest;

/**
 * @author wei
 *
 */
public class MD5Util {
	
    private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
public static void main(String args[]){
	String s=MD5Encode("{\"fpqqlsh\":\"JSHXZS60611001242\",\"nsrsbh\":\"201609140000001\"}","utf-8");
	System.out.println(s);
}
  

}
