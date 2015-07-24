package com.netease.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.netease.exception.HashGenerationException;
 
/**
 * Hash functions utility class.
 */
public class HashGeneratorUtils {
	private final static String DEFAUT_SALT = "roommates_salt_2&g3(*EH";
	
    private HashGeneratorUtils() {
 
    }
    
    /**
     * use default salt for easily using.
     * @param message
     * @return
     * @throws HashGenerationException
     */
    public static String generateSaltMD5(String message) throws HashGenerationException {
    	return hashString(message + DEFAUT_SALT, "MD5");
    }
    
    /**
     *use salt to strength MD5, then store MD5 and salt into database.
     * @param message password
     * @param salt 
     * @return the Salted MD5 hash
     * @throws HashGenerationException
     */
    public static String generateSaltMD5(String message, String salt) throws HashGenerationException {
    	return hashString(message + salt, "MD5");
    }
    
    public static String generateMD5(String message) throws HashGenerationException {
        return hashString(message, "MD5");
    }
 
    public static String generateSHA1(String message) throws HashGenerationException {
        return hashString(message, "SHA-1");
    }
 
    public static String generateSHA256(String message) throws HashGenerationException {
        return hashString(message, "SHA-256");
    }
 
    private static String hashString(String message, String algorithm)
            throws HashGenerationException {
 
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] hashedBytes = digest.digest(message.getBytes("UTF-8"));
 
            return convertByteArrayToHexString(hashedBytes);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            throw new HashGenerationException(
                    "Could not generate hash from String", ex);
        }
    }
 
    private static String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arrayBytes.length; i++) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        return stringBuffer.toString();
    }
}