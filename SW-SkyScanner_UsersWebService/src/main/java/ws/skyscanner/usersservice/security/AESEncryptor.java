package main.java.ws.skyscanner.usersservice.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
 
public class AESEncryptor {
 
	static private final String ENCODING = "UTF-8";
    static private final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    static private final String AES = "AES";
    static private final String KEY = "edumarcialmiw2020";

    public static String Encrypt(String msj) {
        String msjEncrypted = "error_encrypted";
        byte[] msjEncryptedbyte = null;
        byte[] keyByte = null;
        Cipher cp;
        SecretKeySpec sks = null;
        IvParameterSpec ips = null;
        try {
            msjEncryptedbyte = msj.getBytes(ENCODING);
            keyByte = getKeyBytes(KEY);
        } catch (NullPointerException | UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
            return msjEncrypted;
        }

        sks = new SecretKeySpec(keyByte, AES);
        ips = new IvParameterSpec(keyByte);

        try {
            cp = Cipher.getInstance(TRANSFORMATION);
            cp.init(Cipher.ENCRYPT_MODE, sks, ips);
            msjEncryptedbyte = cp.doFinal(msjEncryptedbyte);
            msjEncrypted = new String(Base64.getEncoder().encode(msjEncryptedbyte));
            return msjEncrypted;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            System.out.println(e.getMessage());
            return msjEncrypted;
        }
    }

    public static String Decrypt(String msjEncrypted) {
        String msjDecrypted = "error_decrypted";
        byte[] msjEncryptedByte;
        byte[] keyByte;
        try {
            msjEncryptedByte = Base64.getDecoder().decode(msjEncrypted.getBytes("UTF8"));
            keyByte = getKeyBytes(KEY);
        } catch (NullPointerException | UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
            return msjDecrypted;
        }
        SecretKeySpec sks = new SecretKeySpec(keyByte, AES);
        IvParameterSpec ips = new IvParameterSpec(keyByte);
        try {
            Cipher cp = Cipher.getInstance(TRANSFORMATION);
            cp.init(Cipher.DECRYPT_MODE, sks, ips);
            msjEncryptedByte = cp.doFinal(msjEncryptedByte);
            msjDecrypted = new String(msjEncryptedByte, ENCODING);
            return msjDecrypted;
        } catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            System.out.println(e.getMessage());
            return msjDecrypted;
        }
    }
    
    private static byte[] getKeyBytes(String key) {
        byte[] keyBytes = new byte[16];
        try {
            byte[] parameterKeyBytes = key.getBytes(ENCODING);
            System.arraycopy(parameterKeyBytes, 0, keyBytes, 0, Math.min(parameterKeyBytes.length, keyBytes.length));
        } catch (UnsupportedEncodingException e) {
            System.out.println("[Error][AES][getKeyBytes][0]: " + e.getMessage());
        }
        return keyBytes;
    }
}