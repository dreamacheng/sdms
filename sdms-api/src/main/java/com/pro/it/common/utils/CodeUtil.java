package com.pro.it.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class CodeUtil {

	private static final String AES = "AES";
	private static final String UTF8 = "UTF-8";
	private static final String PRIVATE_KEY = "3273af188536411486ae2639fdcaab12";
	
	
	/**
	 * AES加密
	 * 
	 * @param content
	 * @return
	 * @throws DecoderException
	 */
	private static byte[] encrypt(String content) throws DecoderException {
		try {
			String private_key = PRIVATE_KEY;
			byte[] encodeFormat = null;
			try {
				// 秘钥 Hex解码
				encodeFormat = Hex.decodeHex(private_key.toCharArray());
			} catch (DecoderException e) {
				e.printStackTrace();
			}
			SecretKeySpec key = new SecretKeySpec(encodeFormat, AES);
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			// 加密内容进行编码
			byte[] byteContent = content.getBytes(UTF8);
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, key);
			// 正式执行加密操作
			byte[] result = cipher.doFinal(byteContent);
			return result;
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

	/**
	 * AES解密
	 * 
	 * @param contents
	 * @return
	 * @throws DecoderException
	 */
	private static byte[] decrypt(String contents) throws DecoderException {
		try {
			// 密文使用Hex解码
			byte[] content = Hex.decodeHex(contents.toCharArray());
			// 秘钥 Hex解码
			byte[] encodeFormat = Hex.decodeHex(PRIVATE_KEY.toCharArray());
			SecretKeySpec key = new SecretKeySpec(encodeFormat, AES);
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance(AES);
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.DECRYPT_MODE, key);
			// 正式执行解密操作
			byte[] result = cipher.doFinal(content);
			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Aes加密
	 * 
	 * @param context     明文
	 * @return
	 * @throws DecoderException
	 */
	public static String encryption(String context) throws DecoderException {
		// 加密
		byte[] encryptResult = encrypt(context);
		// 密码文Hex编码
		String encryptResultStr = Hex.encodeHexString(encryptResult);
		return encryptResultStr;
	}

	/**
	 * Aes解密
	 * 
	 * @param context     密文
	 * @return
	 * @throws DecoderException
	 * @throws UnsupportedEncodingException
	 */
	public static String decryption(String context)
			throws DecoderException, UnsupportedEncodingException {
		// 解密
		byte[] decryptResult = decrypt(context);
		String result = new String(decryptResult, UTF8);
		return result;
	}
	
}
