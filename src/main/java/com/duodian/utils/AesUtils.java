package com.duodian.utils;


import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Encoder;

public class AesUtils {
	private static final byte[] key = "dea9CXdMFbctHrTjUnql8mIq6bJdi4ZM".getBytes();
	private static final byte[] iv = "4960493827918392".getBytes();;
	private static final String mode = "AES/CBC/PKCS5Padding";
	
	public static String Encrypt(String sSrc) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance(mode);//"算法/模式/补码方式"
		IvParameterSpec ivParam = new IvParameterSpec(iv);//使用CBC模式，需要一个向量iv，可增加加密算法的强度
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParam);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf8"));

		return new String(Base64.encodeBase64(encrypted));
	}

	
	public static String Decrypt(String sSrc) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance(mode);
		IvParameterSpec ivParam = new IvParameterSpec(iv);
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParam);
		byte[] encrypted1 = Base64.decodeBase64(sSrc.getBytes());
		try {
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original);
			return originalString;
		} catch (Exception e) {
			return null;
		}
	}
	
	private static final byte[] Loginkey = "E572F20EDB30984A24235B8014535A47".getBytes();
	private static final byte[] Loginiv = "4960432827928592".getBytes();;
	private static final String Loginmode = "AES/CBC/PKCS5Padding";
	
	public static String LoginEncrypt(String sSrc) {
		String result = null;
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(Loginkey, "AES");
			Cipher cipher = Cipher.getInstance(Loginmode);//"算法/模式/补码方式"
			IvParameterSpec ivParam = new IvParameterSpec(Loginiv);//使用CBC模式，需要一个向量iv，可增加加密算法的强度
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParam);
			byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf8"));
			result = new String(Base64.encodeBase64(encrypted));
		} catch (Exception e) {
			e.printStackTrace();
		} 

		return result;
	}

	
	public static String LoginDecrypt(String sSrc) {
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(Loginkey, "AES");
			Cipher cipher = Cipher.getInstance(Loginmode);
			IvParameterSpec ivParam = new IvParameterSpec(Loginiv);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParam);
			byte[] encrypted1 = Base64.decodeBase64(sSrc.getBytes());
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original);
			return originalString;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public static byte[] encrypt(String content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
//			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			SecretKeySpec key = new SecretKeySpec(new byte[]{83, 106, 39, 63, 56, 125, -125, -113, -38, 102, -102, 64, 47, -85, -29, 10}, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			byte[] byteContent = content.getBytes("UTF-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}
	
	//流量充值加密
	public static String aesEncrypt(String content, String password) {
	    byte[] result = null;
	    try {
	      byte[] enCodeFormat = password.getBytes();
	      SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
	      Cipher cipher = Cipher.getInstance("AES");
	      byte[] byteContent = content.getBytes("UTF-8");
	      cipher.init(1, key);
	      result = cipher.doFinal(byteContent);
	      return new BASE64Encoder().encode(result);
	    } catch (Exception e) {
	      throw new RuntimeException(e);
	    }
	}
	//保险加密 ECB
	public static String encryptDES(String encryptString, String encryptKey) throws Exception {  
        SecretKeySpec key = new SecretKeySpec(getKey(encryptKey), "DES");  
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");  
        cipher.init(Cipher.ENCRYPT_MODE, key);  
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes("utf-8"));  
        return new BASE64Encoder().encode(encryptedData);  
    } 
	 public static byte[] getKey(String keyRule) {  
	        Key key = null;  
	        byte[] keyByte = keyRule.getBytes();  
	        byte[] byteTemp = new byte[8];  
	        for (int i = 0; i < byteTemp.length && i < keyByte.length; i++) {  
	            byteTemp[i] = keyByte[i];  
	        }  
	        key = new SecretKeySpec(byteTemp, "DES");  
	        return key.getEncoded();  
	    }  
	public static void main(String[] args) throws Exception{
		System.out.println(LoginEncrypt("5E6DB88A0EC3904D4F197B26F1BA1A61"));
		System.out.println(aesEncrypt("110703","test111112311231"));
	}

}
