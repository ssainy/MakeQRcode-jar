import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class Base64Util {
    private static final Logger LOGGER = Logger.getLogger(Base64Util.class);
	
	private static final String CHARSET = "GBK";
	
	/**
	 * <p>解base64</p>
	 * 
	 * @param res
	 * @return String
	 * @author: lichunhui lichunhui1314@126.com
	 * @date: Created on 2014-11-17 上午11:17:06
	 */
	public static String encode(String res){
		try{
			Base64 base = new Base64();
			if(res != null && !"".equals(res)){ // 即将解密串不为null并且不为“”
				return new String(base.encode(res.getBytes(CHARSET)));
			}else {
				LOGGER.warn("即将解密串为null或者为“” ");
				return "";
			}
		}catch (Exception e) {
			//LogUtil.ErrorLogMessage("未知：" + e);
			return "";
		}
	}
	/**
	 * 
	 * <p>加密UTF-8</p>
	 * 
	 * @param res
	 * @return String
	 * @author: caolucai Free.cao@orchestrallinc.cn
	 * @date: Created on 2015-11-5 下午03:48:25
	 */
	public static String encodeutf8(String res){
		try{
			Base64 base = new Base64();
			if(res != null && !"".equals(res)){ // 即将解密串不为null并且不为“”
				return new String(base.encode(res.getBytes("UTF-8")));
			}else {
				LOGGER.warn("即将解密串为null或者为“” ");
				return "";
			}
		}catch (Exception e) {
			//LogUtil.ErrorLogMessage("未知：" + e);
			return "";
		}
	}
	/**
	 * 
	 * <p>base64编码</p>
	 * 
	 * @param res
	 * @return byte[]
	 * @author: lichunhui lichunhui1314@126.com
	 * @date: Created on 2014-11-17 上午11:41:19
	 */
	public static byte[] encode(byte[] res){
		try{
			Base64 base = new Base64();
			return base.encode(res);
		}catch (Exception e) {
			//LogUtil.ErrorLogMessage("未知：" + e);
			return null;
		}
	}

	/**
	 * 
	 * <p>解base64编码</p>
	 * 
	 * @param str
	 * @return
	 * @author: lichunhui lichunhui1314@126.com
	 * @date: Created on 2014-11-17 上午11:41:41
	 */
	public static String decode(String str) {
		
		try{
			return new String(new Base64().decode(str.getBytes()),CHARSET);
		}catch (Exception e) {
			//LogUtil.ErrorLogMessage("未知：" + e);
			return null;
		}
	}

	/**
	 * 
	 * <p>解码成utf-8</p>
	 * 
	 * @param str
	 * @return String
	 * @author: caolucai Free.cao@orchestrallinc.cn
	 * @date: Created on 2015-11-5 下午03:47:25
	 */
	public static String decodeutf8(String str) {
		
		try{
			return new String(new Base64().decode(str.getBytes()),"UTF-8");
		}catch (Exception e) {
			//LogUtil.ErrorLogMessage("未知：" + e);
			return null;
		}
	}
	
	public static byte[] decodeByte(byte[] str) {
		return new Base64().decode(str);
	}
	

	/**
	 * 
	 * <p>解base64编码</p>
	 * 
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException byte[]
	 * @author: lichunhui lichunhui1314@126.com
	 * @date: Created on 2014-11-17 上午11:41:58
	 */
	public static byte[] decode(byte[] str) throws UnsupportedEncodingException {
		return new Base64().decode(str);
	}
}
