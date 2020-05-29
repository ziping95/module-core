package com.wzp.module.core.utils;

import java.util.Base64;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * BASE64算法实现加解密
 */
public class Base64Util {

	/**
	 * base64算法加密
	 * @param data
	 * @return
	 */
	public static String base64Encrypt(byte[] data){
		return new String(Base64.getEncoder().encode(data));
	}
	
	/**
	 * base64算法解密
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String base64Decrypt(String data) throws Exception{
		return new String(base64DecryptByte(data));
	}

	/**
	 * base64算法解密
	 * @param data
	 * @return 字节数组
	 * @throws Exception
	 */
	public static byte[] base64DecryptByte(String data) throws Exception {
		return Base64.getDecoder().decode(data);
	}
}
