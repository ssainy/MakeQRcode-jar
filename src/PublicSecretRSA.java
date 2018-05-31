import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;
import javax.swing.*;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 非对称加密RSA 公钥加密-私钥解密
 */
public class PublicSecretRSA {


	private static final Logger LOGGER = LoggerFactory.getLogger(PublicSecretRSA.class);
	private static final String RSA = "RSA";
	private static final String UTF_8 = "UTF-8";
	private static String KEY_PUBLIC_KEY = null;
	private static String KEY_PRIVATE_KEY =null;

	private static final String KEY_PRIDATA_DATA = "key_priData.data";
   /**
    * RSA最大加密明文大小
    */
   private static final int MAX_ENCRYPT_BLOCK = 117;
   /**
    * RSA最大解密密文大小
    */
   private static final int MAX_DECRYPT_BLOCK = 128;
	private static Key publicKey = null;
	private static Key privateKey = null;
	/**
	 * 生成秘钥
	 * 
	 * @throws Exception
	 */
        
	private static void makeKey() {
		try {
			// 生成钥匙
			KeyPairGenerator keyPairGenerator = KeyPairGenerator
					.getInstance(RSA);
			keyPairGenerator.initialize(1024);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			publicKey = keyPair.getPublic();
			privateKey = keyPair.getPrivate();
		} catch (Exception e) {
			LOGGER.error("生成密钥出错...", e);
		}
	}

	/**
	 * 公钥加密
	 * 
	 * @throws Exception
	 */
	public static byte[] publicEncrypt(String content,String path) {
            System.out.println("In publicEncrypt....");
		System.out.println("IN Static...");
		System.out.println(System.getProperty("user.dir"));

		File file = new File(path);
		if (!file.exists()) {
			makeKey();
			// 保存秘钥
			saveKey(publicKey, path);
			saveKey(privateKey, path);
			saveRSAPublicKeyAsDotNetFormatToXmlFile(publicKey, path);
		} else {
			System.out.println("else......");
			publicKey = readKey(path);
			privateKey = readKey(path);
		}
		byte[] results = null;
		try {
			Cipher cipher = Cipher.getInstance(RSA);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] data = content.getBytes(UTF_8);
			int inputLen = data.length;
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        int offSet = 0;
	        byte[] cache;
	        int i = 0;
	        // 对数据分段加密
	        while (inputLen - offSet > 0) {
	            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
	                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
	            } else {
	                cache = cipher.doFinal(data, offSet, inputLen - offSet);
	            }
	            out.write(cache, 0, cache.length);
	            i++;
	            offSet = i * MAX_ENCRYPT_BLOCK;
	        }
	        results = out.toByteArray();
	        out.close();
		} catch (Exception e) {
			LOGGER.error("公钥加密出错...", e);
		}
		return results;
	}

	/**
	 * 私钥解密
	 * 
	 * @throws Exception
	 */
	public static String privateDecrypt(byte[] encryptStr,String path) {
		String str = null;
		System.out.println("IN Static...");
		System.out.println(System.getProperty("user.dir"));

		File file = new File(path);
		if (!file.exists()) {
			makeKey();
			// 保存秘钥
			saveKey(publicKey, path);
			saveKey(privateKey, path);
			saveRSAPublicKeyAsDotNetFormatToXmlFile(publicKey, path);
		} else {
			System.out.println("else......");
			publicKey = readKey(path);
			privateKey = readKey(path);
		}
		try {

			Cipher cipher = Cipher.getInstance(RSA);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			//str = new String(cipher.doFinal(encryptStr), UTF_8);
			int inputLen = encryptStr.length;
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        int offSet = 0;
	        byte[] cache;
	        int i = 0;
	        // 对数据分段解密
	        while (inputLen - offSet > 0) {
	            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
	                cache = cipher.doFinal(encryptStr, offSet, MAX_DECRYPT_BLOCK);
	            } else {
	                cache = cipher.doFinal(encryptStr, offSet, inputLen - offSet);
	            }
	            out.write(cache, 0, cache.length);
	            i++;
	            offSet = i * MAX_DECRYPT_BLOCK;
	        }
	        byte[] decryptedData = out.toByteArray();
	        out.close();
	        str = new String(decryptedData, UTF_8);
		} catch (Exception e) {
			LOGGER.error("私钥解密出错...", e);
		}
		return str;
	}



	/**
	 * 保存密钥
	 * 
	 * @param key
	 * @param keyName
	 * @throws Exception
	 */
	public static void saveKey(Key key, String keyName) {
		FileOutputStream foskey = null;
		ObjectOutputStream oos = null;
		try {
                        foskey = new FileOutputStream(keyName);
			oos = new ObjectOutputStream(foskey);
			oos.writeObject(key);
		} catch (Exception e) {
			LOGGER.error("保存密钥出错...", e);
		} finally {
			try {
				if (oos != null) {
					oos.close();
				}
				if (foskey != null) {
					foskey.close();
				}
			} catch (Exception e) {
				LOGGER.error("关闭流出错...", e);
			}
		}
	}

	/**
	 * 读取密钥
	 * 
	 * @param keyName
	 * @return Key
	 * @throws Exception
	 */
	public static Key readKey(String keyName) {
		FileInputStream fiskey = null;
		ObjectInputStream oiskey = null;
		Key key = null;
		try {
			//fiskey = new FileInputStream(SystemConfig.rsaKeySavePath + keyName);
                        fiskey = new FileInputStream(keyName);
			oiskey = new ObjectInputStream(fiskey);
			key = (Key) oiskey.readObject();
		} catch (Exception e) {
			LOGGER.error("读取密钥出错...", e);
		} finally {
			try {
				if (oiskey != null) {
					oiskey.close();
				}
				if (fiskey != null) {
					fiskey.close();
				}
			} catch (Exception e) {
				LOGGER.error("关闭流出错...", e);
			}
		}
		return key;
	}

	/**
	 * 保存公钥为.NET平台格式XML文件
	 * 
	 * @param key
	 * @param keyName
	 */
	public static void saveRSAPublicKeyAsDotNetFormatToXmlFile(Key key,
			String keyName) {
		try {
			StringBuffer buff = new StringBuffer();
			RSAPublicKey rsaPublicKey = (RSAPublicKey) key;
			buff.append("<RSAKeyValue>");
			buff.append("<Modulus>");
			buff.append(Base64Encoding.encryptBASE64((removeMSZero(rsaPublicKey
					.getModulus().toByteArray()))));
			buff.append("</Modulus>");
			buff.append("<Exponent>");
			buff.append(Base64Encoding.encryptBASE64((removeMSZero(rsaPublicKey
					.getPublicExponent().toByteArray()))));
			buff.append("</Exponent>");
			buff.append("</RSAKeyValue>");
			String keyString = buff.toString().replaceAll("[ \t\n\r]", "");
			//FileUtils.writeStringToFile(new File(SystemConfig.rsaKeySavePath + keyName + ".xml"), keyString);
                        FileUtils.writeStringToFile(new File(System.getProperty("user.dir") + "\\"
					+ keyName + ".xml"), keyString);
		} catch (Exception e) {
			LOGGER.error("保存公钥为.NET平台格式XML文件出错...", e);
		}
	}

	/**
	 * remove leading (Most Significant) zero byte if present
	 * 
	 * @param data
	 * @return
	 */
	private static byte[] removeMSZero(byte[] data) {
		byte[] data1;
		int len = data.length;
		if (data[0] == 0) {
			data1 = new byte[data.length - 1];
			System.arraycopy(data, 1, data1, 0, len - 1);
		} else {
			data1 = data;
		}
		return data1;
	}

}
